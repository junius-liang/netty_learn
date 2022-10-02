## 1、Java IO概述



## 2．Java IO模型介绍

#### 2.1 基本IO说明

**I/O 模型简单的理解**：就是用什么样的[通道](https://so.csdn.net/so/search?q=%E9%80%9A%E9%81%93&spm=1001.2101.3001.7020)进行数据的发送和接收，很大程度上决定了程序通信的性能  
主要有三种模型：`BIO、NIO、AIO`

1. `Java BIO` ： 同步并阻塞(传统阻塞型)，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销。【*俗话说，占着茅坑不拉屎，这个茅坑还是得被占着*】
   
   ![](./images/1.png)
2. `Java NIO` ： 同步非阻塞，服务器实现模式为一个线程处理多个请求(连接)，即客户端发送的连接请求都会注册到多路复用器上，多路复用器（`Selector`）**轮询**到连接有`I/O`请求就进行处理。【*专门有一个老大哥在巡逻，没有事儿，别占着位置*】
   
   ![](./images/2.png)
3. `Java AIO`： 异步非阻塞，`AIO` 引入异步通道的概念，采用了 `Proactor`模式，简化了程序编写，有效的请求才启动线程，它的特点是先由操作系统完成后才通知服务端程序启动线程去处理，一般适用于连接数较多且连接时间较长的应用。



## 3. BIO工作机制

是一种阻塞的IO，本质是一个连接一个线程。效率低、资源浪费程度高。

**如何进行BIO编程？**

1. 服务器端启动一个`ServerSocket`

2. 客户端启动`Socket`对服务器进行通信，默认情况下服务器端需要对每个客户 建立一个线程与之通讯

3. 客户端发出请求后, 先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝

4. 如果有响应，客户端线程会等待请求结束后，在继续执行

![](./images/3.png)

看上面的分析图，可以发现阻塞IO会有两个地方会发生阻塞，

1. 如果没有客户端来建立连接，服务器会阻塞
2. 如果客户端没有输入，服务器也会阻塞。

### Java nio
客户端->buffer->channel->selector->server

通俗理解：NIO是可以做到用一个线程来处理多个操作的。假设有10000个请求过来,根据实际情况，可以分配50或者100个线程来处理。不像之前的阻塞IO那样，非得分配10000个。

### Selector、Channel、Buffer的关系
1. 一个Channel对应一个Buffer
2. Selector对应一个线程，一个线程对应多个channel
3. 程序切换到哪个channel是由时间决定的
4. Selector根据不同事件在各个通道上切换
5. Buffer是一个内存块，底层是数组
6. Buffer读写切换要flip

### Buffer
##### Buffer使用
Buffer有多个类型：IntBuffer、DoubleBuffer等八大数据类型（布尔类型没有）
构造函数 IntBuffer.allocate(size) 可以存放5个int
存放方法 put()
容量 capacity()
读写切换 flip()
判断buffer是否还有内容 hasRemaining()
获取数据 get()

案例：com.chapter2.Demo1

##### Buffer的参数讲解
```
  private int mark = -1; //标记
  private int position = 0; //标记被读写的元素的索引
  private int limit; //缓冲区的当前终点
  private int capacity; //容量，被设定不可改变
```
### Channel
Channel有多种实现类：FileChannel、DatagramChannel、SocketChannel、ServerSocketChannel

FileChannel：read(ByteBuffer a) 从channel读取数据放到buffer
write(ByteBuffer b) 把buffer的数据写到channnel
transferFrom 从目标通道复制数据到当前通道
transferTo 从当前通道复制数据到目标通道

##### 案例一：将字符串写到文件中
com.chapter2.Demo2

##### 案例二：将文件输出到控制台
com.chapter2.Demo3


### Selector
##### 基本介绍
Java 的 NIO，用非阻塞的 IO 方式。可以用一个线程，处理多个的客户端连接，就会使用到Selector(选择器)

Selector能够检测多个注册的通道上是否有事件发生(注意:多个Channel以事件的方式可以注册到同一个Selector)，如果有事件发生，便获取事件然后针对每个事件进行相应的处理。这样就可以只用一个单线程去管理多个通道，也就是管理多个连接和请求。

只有在 连接/通道 真正有读写事件发生时，才会进行读写，就大大地减少了系统开销，并且不必为每个连接都创建一个线程，不用去维护多个线程

避免了多线程之间的上下文切换导致的开销

##### Selector特点说明
Netty的IO线程NioEventLoop聚合了Selector（选择器，也叫多路复用器），可以同时并发处理成百上千个客户端连接。

当线程从某客户端 Socket 通道进行读写数据时，若没有数据可用时，该线程可以进行其他任务。

线程通常将非阻塞 IO 的空闲时间用于在其他通道上执行 IO 操作，所以单独的线程可以管理多个输入和输出通道。

由于读写操作都是非阻塞的，这就可以充分提升 IO 线程的运行效率，避免由于频繁I/O 阻塞导致的线程挂起。

一个 I/O 线程可以并发处理 N 个客户端连接和读写操作，这从根本上解决了传统同步阻塞 I/O 一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升。

##### 如何工作？
1、 当客户端连接时，可以通过ServerSocketChannel得到SocketChannel。

2、可以通过register(Selector sel, int ops)，将SocketChannel注册到Selector上，并且一个selector上可以注册多个SocketChannel。

3、注册之后会得到SelectionKey，会与该Selector进行关联。

4、Selector进行监听select方法，并且返回有事件发生的通道的个数。并且进一步得到有事件发生的SelectionKey

5、通过SelectorKey的channel()方法，反向获取SocketChannel。

6、上一步可以得到Channel，然后完成业务的处理。

![](./images/4.png)

##### api




