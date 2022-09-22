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


