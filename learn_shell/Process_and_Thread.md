## 基本知识
- **单核CPU执行多任务**：  
各任务交替执行，因执行速度很快，所以感觉是在同时进行，并不是真正的并行执行任务。***真正的并行执行只能有多核CPU实现。***
- **进程和线程**：  
  - 线程是最小的执行单元，而进程由至少一个线程组成。  
  - 对于操作系统来说，一个任务就是一个进程（Process），比如打开一个浏览器就是启动一个浏览器进程，打开一个记事本就启动了一个记事本进程，打开两个记事本就启动了两个记事本进程，打开一个Word就启动了一个Word进程。  
  - 一般进程有以下三个特征
    - 独立性：进程是系统中独立存在的实体，它可以拥有自己的独立的资源，每一个进程都拥有自己的私有的地址空间。在没有经过进程本身允许的情况下，一个用户进程不可以直接访问其他进程的地址空间。
    - 动态性：进程与程序的区别在于，程序只是一个静态的指令集合，而进程是一个正在系统中活动的指令集合。在进程中加入了时间的概念。进程具有自己的生命周期和各种不同的状态，在程序中是没有这些概念的。
    - 并发性：多个进程可以在单个处理器上并发执行，多个进程之间不会互相影响。
  - 有些进程还不止同时干一件事，比如Word，它可以同时进行打字、拼写检查、打印等事情。在一个进程内部，要同时干多件事，就需要同时运行多个“子任务”，我们把进程内的这些“子任务”称为线程（Thread）。
- **多任务实现方式**：
  - 1、多进程模式：多个进程，诶个进程一个线程
  - 2、多线程模式：单个进程中多个线程
  - 3、多线程多进程模式：多个进程，每个进程多个线程（极少采用，太过复杂）
- **多线程和多进程的最大区别**：  
多进程中每个进程均有一份变量的拷贝，进程间互不影响；二多线程共享同样的变量，共享数据，可能存在多个线程同时修改某一个变量的情况。
 ## 多进程multiprocessing
- **os.fork()**  
此函数仅适用于linux/unix系统！普通的函数调用，调用一次，返回一次，但是fork()调用一次，返回两次，因为操作系统自动把当前进程（称为父进程）复制了一份（称为子进程），然后，分别在父进程和子进程内返回。  
子进程永远返回0，而父进程返回子进程的ID。这样做的理由是，一个父进程可以fork出很多子进程，所以，父进程要记下每个子进程的ID，而子进程只需要调用getppid()就可以拿到父进程的ID。
  - 示例：
  ```
  import os
  
  print('Process (%s) start...' % os.getpid())
  # Only works on Unix/Linux/Mac:
  pid = os.fork()
  if pid == 0:
      print('I am child process (%s) and my parent is %s.' % (os.getpid(), os.getppid()))
  else:
      print('I (%s) just created a child process (%s).' % (os.getpid(), pid))
  
  # 输出结果：
  # Process (876) start...
  # I (876) just created a child process (877).
  # I am child process (877) and my parent is 876.
  ```
- **multiprocessing**
  - multiprocessing模块提供了一个Process类来代表一个进程对象
  - 示例：启动一个子进程并等待其结束：
  ```
  from multiprocessing import Process
  import os

  # 子进程要执行的代码
  def run_proc(name):
      print('Run child process %s (%s)...' % (name, os.getpid()))

  if __name__=='__main__':
      print('Parent process %s.' % os.getpid())
      p = Process(target=run_proc, args=('test',))  # 创建子进程，即一个Process实例，参数为子进程运行的函数
      print('Child process will start.')
      p.start() # 启动子进程
      p.join() # 等待子进程结束后再继续往下运行，通常用于进程间的同步
      print('Child process end.')
  # 结果输出
  # Parent process 13720.
  # Child process will start.
  # Run child process test (14904)...
  # Child process end.
  ```
## 多线程Threading（python使用多线程并不能提效）
启动一个线程就是把要执行的函数传入一个Thread实例中，然后调用start()开始。默认的线程为主线程，名称为MainThread;自己启动的线程为子线程，需要指定名称，Python‘自动名称为Thread-1...
- **threading**
  - **常用函数**：
  获取当前线程实例：threading.current_thread()  
  获取实例名称：threading.current_thread().name  
  创建线程：threading.Thread(target=func, name='str')  
  - **示例**：
  ```
  import time, threading

  # 新线程执行的代码:
  def loop():
      print('thread %s is running...' % threading.current_thread().name)
      n = 0
      while n < 5:
          n = n + 1
          print('thread %s >>> %s' % (threading.current_thread().name, n))
          time.sleep(1)
  print('thread %s ended.' % threading.current_thread().name)

  print('thread %s is running...' % threading.current_thread().name)
  t = threading.Thread(target=loop, name='LoopThread')
  t.start() # 和进程类似
  t.join()
  print('thread %s ended.' % threading.current_thread().name)
  # 输出
  '''
  thread MainThread is running...
  thread LoopThread is running...
  thread LoopThread >>> 1
  thread LoopThread >>> 2
  thread LoopThread >>> 3
  thread LoopThread >>> 4
  thread LoopThread >>> 5
  thread LoopThread ended.
  thread MainThread ended.
  '''
  ```

启动与CPU核心数量相同的N个线程，在4核CPU上可以监控到CPU占用率仅有102%，也就是仅使用了一核。  

但是用C、C++或Java来改写相同的死循环，直接可以把全部核心跑满，4核就跑到400%，8核就跑到800%，为什么Python不行呢？  

因为Python的线程虽然是真正的线程，但解释器执行代码时，有一个GIL锁：Global Interpreter Lock，任何Python线程执行前，必须先获得GIL锁，然后，每执行100条字节码，解释器就自动释放GIL锁，让别的线程有机会执行。这个GIL全局锁实际上把所有线程的执行代码都给上了锁，所以，多线程在Python中只能交替执行，即使100个线程跑在100核CPU上，也只能用到1个核。  

GIL是Python解释器设计的历史遗留问题，通常我们用的解释器是官方实现的CPython，要真正利用多核，除非重写一个不带GIL的解释器。  

所以，在Python中，可以使用多线程，但不要指望能有效利用多核。如果一定要通过多线程利用多核，那只能通过C扩展来实现，不过这样就失去了Python简单易用的特点。  

不过，也不用过于担心，Python虽然不能利用多线程实现多核任务，但可以通过多进程实现多核任务。多个Python进程有各自独立的GIL锁，互不影响。  
### 并发和并行
并发（Concurrency）和并行（Parallel）是两个概念，并行指在同一时刻有多条指令在多个处理器上同时执行；并发才旨在同一时刻只能有一条指令执行，但多个进程指令被快速轮换执行，使得在宏观上具有多个进程同时执行的效果
- 多个进程可以在**单个处理器**上并发执行，多个进程之间不会互相影响
- 一个线程可以创建和撤销另一个线程，**同一个进程**中的多个线程之间可以并发运行。
### 多线程的好处
- 进程之间不能共享内存，但线程之间共享内存非常容易。
- 操作系统在创建进程时，需要为该进程重新分配系统资源，但创建线程的代价则小得多。因此，使用多线程来实现多任务并发执行比使用多进程的效率高。
- Python 语言内置了多线程功能支持，而不是单纯地作为底层操作系统的调度方式，从而简化了 Python 的多线程编程。
