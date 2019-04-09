## Spring MVC 异步请求 Demo

以DeferredResult 为例,详细的演示了如何进行异步处理

测试接口
- http://127.0.0.1:8080/create-order //异步请求接口,生成一个uuid;可以使用 Apache ab 进行压测,再调用下面两个接口查看实时数据
- http://127.0.0.1:8080/get-size    //获取map大小和Redis中task1队列大小
- http://127.0.0.1:8080/get-all-map // 获取map中所有数据