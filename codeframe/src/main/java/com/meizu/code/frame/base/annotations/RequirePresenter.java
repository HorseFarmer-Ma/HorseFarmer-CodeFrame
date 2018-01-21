package com.meizu.code.frame.base.annotations;

import com.meizu.code.frame.base.frame.mvp.BasePresenter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Presenter注解类
 *
 * Created by maxueming on 17-11-13.
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePresenter {
    Class<? extends BasePresenter> value();
}
