### Java BIO
同步并阻塞(传统阻塞型)，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销。【俗话说，占着茅坑不拉屎，这个茅坑还是得被占着】

![](D:\project\study\netty\demo1_bio\src\main\resources\images\img.png)

### 使用BIO编程
1. 服务器端启动一个ServerSocket
2. 客户端启动Socket对服务器进行通信，默认情况下服务器端需要对每个客户 建立一个线程与之通讯
3. 客户端发出请求后, 先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝
4. 如果有响应，客户端线程会等待请求结束后，在继续执行

![](D:\project\study\netty\demo1_bio\src\main\resources\images\img_1.png)

### 代码演示
详见 com.chapter1.Demo1

### 客户端
使用telnet ip 端口 连接到客户端
ctrl+] 键
send 消息内容
