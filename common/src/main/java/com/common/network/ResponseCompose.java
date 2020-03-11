package com.common.network;

import com.common.bean.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseCompose {


    public static <T> FlowableTransformer<BaseResponse<T>, BaseResponse<T>> parseResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, Flowable<? extends BaseResponse<T>>> {

        @Override
        public Flowable<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Flowable.error(DefaultNetExceptionParser.parse(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, Flowable<BaseResponse<T>>> {

        @Override
        public Flowable<BaseResponse<T>> apply(BaseResponse<T> tResponse) throws Exception {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            if (code == DefaultNetExceptionParser.SUCCESS) {
                return Flowable.just(tResponse);
            } else {
                return Flowable.error(new ApiException(code, message));
            }
        }
    }

}


