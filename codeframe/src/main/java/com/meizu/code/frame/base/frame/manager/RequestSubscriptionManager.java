package com.meizu.code.frame.base.frame.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.exceptions.Exceptions;

/**
 * 订阅类型管理类，供BaseLoader储存订阅
 * <p>
 * All methods of this class are thread-safe.
 */
public final class RequestSubscriptionManager<T> implements Subscription {

    private HashMap<T, Subscription> mSubscriptionManagers;
    private volatile boolean mUnsubscribed;

    public RequestSubscriptionManager() {
        mSubscriptionManagers = new HashMap<>();
    }

    @Override
    public boolean isUnsubscribed() {
        return mUnsubscribed;
    }

    /**
     * Adds a new {@link Subscription} to this {@code RequestSubscriptionManager} if the
     * {@code RequestSubscriptionManager} is not yet unsubscribed. If the {@code RequestSubscriptionManager} <em>is</em>
     * unsubscribed, {@code add} will indicate this by explicitly unsubscribing the new {@code Subscription} as
     * well.
     *
     * @param s the {@link Subscription} to add
     */
    public void add(final T key, final Subscription s) {
        if (s.isUnsubscribed()) {
            return;
        }
        if (!mUnsubscribed) {
            synchronized (this) {
                if (!mUnsubscribed) {
                    if (mSubscriptionManagers == null) {
                        mSubscriptionManagers = new HashMap<>();
                    }
                    mSubscriptionManagers.put(key, s);
                    return;
                }
            }
        }
        s.unsubscribe();
    }

    /**
     * Removes a {@link Subscription} from this {@code RequestSubscriptionManager}, and unsubscribes the
     * {@link Subscription}.
     *
     * @param key the {@link T} to remove
     */
    public void remove(final T key) {
        if (!mUnsubscribed) {
            Subscription subscription = null;
            synchronized (this) {
                if (mUnsubscribed || mSubscriptionManagers == null) {
                    return;
                }
                subscription = mSubscriptionManagers.remove(key);
            }

            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    /**
     * Unsubscribes itself and all inner subscriptions.
     * <p>After call of this method, new {@code Subscription}s added to {@link RequestSubscriptionManager}
     * will be unsubscribed immediately.
     */
    @Override
    public void unsubscribe() {
        if (!mUnsubscribed) {
            HashMap<T, Subscription> unsubscribe = null;
            synchronized (this) {
                if (mUnsubscribed) {
                    return;
                }
                mUnsubscribed = true;
                unsubscribe = mSubscriptionManagers;
                mSubscriptionManagers = null;
            }
            // we will only get here once
            unsubscribeFromAll(unsubscribe);
        }
    }

    private static <V> void unsubscribeFromAll(HashMap<V, Subscription> s) {
        if (s == null) {
            return;
        }
        List<Throwable> es = null;
        for (Map.Entry<V, Subscription> entry : s.entrySet()) {
            try {
                entry.getValue().unsubscribe();
            } catch (Throwable e) {
                if (es == null) {
                    es = new ArrayList<Throwable>();
                }
                es.add(e);
            }
        }
        Exceptions.throwIfAny(es);
    }

    /**
     * Returns true if this composite is not unsubscribed and contains subscriptions.
     *
     * @return {@code true} if this composite is not unsubscribed and contains subscriptions.
     * @since 1.0.7
     */
    public boolean hasSubscriptions() {
        if (!mUnsubscribed) {
            synchronized (this) {
                return !mUnsubscribed && mSubscriptionManagers != null && !mSubscriptionManagers.isEmpty();
            }
        }
        return false;
    }

    public int size() {
        return mSubscriptionManagers != null ? mSubscriptionManagers.size() : 0;
    }

    public boolean containsKey(T key) {
        if (mSubscriptionManagers != null) {
            synchronized (this) {
                return mSubscriptionManagers != null && mSubscriptionManagers.containsKey(key);
            }
        }
        return false;
    }
}
