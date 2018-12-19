package com.meizu.code.frame.base.annotations;

import com.meizu.code.frame.base.frame.mvp.BasePresenter;
import com.meizu.code.frame.base.frame.mvp.BeamView;

/**
 * 注解辅助类
 *
 * Created by maxueming on 17-11-15.
 */

public class AnnotationsHelper {

    /**
     * 通过注解创建BeamView实体类
     *
     * @param object
     * @param <BeamViewInstance>
     * @return
     */
    public static <BeamViewInstance extends BeamView> BeamViewInstance createBeamClass(Object object) {
        RequireBeamView annotation = getAnnotation(object, RequireBeamView.class);
        Class<BeamViewInstance> mBeamViewClazz = (Class<BeamViewInstance>) annotation.value();
        BeamViewInstance mBeamViewInstance = null;
        try {
            mBeamViewInstance = mBeamViewClazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("newInstance: mBeamViewClazz.newInstance() fail!");
        }
        return mBeamViewInstance;
    }

    /**
     * 通过注解创建BasePresenter实体类
     *
     * @param object
     * @param <BasePresenterInstance>
     * @return
     */
    public static <BasePresenterInstance extends BasePresenter> BasePresenterInstance createPresenterClass(Object object) {
        RequirePresenter annotation = getAnnotation(object, RequirePresenter.class);
        Class<BasePresenterInstance> mBasePresenterClazz = (Class<BasePresenterInstance>) annotation.value();
        BasePresenterInstance mBasePresenterInstance = null;
        try {
            mBasePresenterInstance = mBasePresenterClazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("newInstance: mBasePresenterClazz.newInstance() fail!");
        }
        return mBasePresenterInstance;
    }

    private static <T, M> M getAnnotation(T t, Class<M> annotationTypeClazz) {
        Class clazz = t.getClass();
        M annotation = (M) clazz.getAnnotation(annotationTypeClazz);
        if (annotation == null) {
            throw new IllegalArgumentException("Get " + annotationTypeClazz.getSimpleName()
                    + " annotation fail, please set it in "+ clazz.getSimpleName() + " first!");
        }
        return annotation;
    }
}
