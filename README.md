# RXJava+Retrofit框架使用说明

> author: zzh

本框架基于`RXJava2.0`及`Retrofit2.3`开发，封装了使用这两个框架进行网络请求过程中常见的线程切换，OKHttpClient对象创建，缓存等工作；

使用方法：

```java
//获取APIService对象
APIService service = ApiFactory.getAPIService(APIService.class, BASE_URL, MainActivity.this);
        service.getLatestNews()
                //指定subscribeOn在IO线程，observerOn发生在主线程
                .compose(RXSchedulers.applyIOMain())
          		//绑定Activity的生命周期，当Activity Destroy时，停止网络请求
                .compose(bindUntilEvent(ActivityLifecycleEvent.DESTROY))
                .subscribe(new Observer<Latest>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Latest latest) {
                                   Log.d("MainActivity", latest.toString());
                                   mTextView.setText(latest.toString());
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", " error: " + e.toString());
                               }

                               @Override
                               public void onComplete() {

                               }
                           }

                );
```





