### 使用方法总结：两种方式(string操作)
#### 〇、踩坑集合
1、 decode_responses设置
> 在新建连接或者连接池时,加入参数decode_responses=True; 
此参数默认为false，为false时.从redis取值的类型为bytes类型，并且编码类型不为utf-8; 
参考 https://www.jianshu.com/p/495f401a46e8
#### 一、直接使用
每个Redis实例都会建立一个ConnectionPool
```
# -*- conding:utf-8 -*-
# 1、导入包
import redis
host = '127.0.0.1' # 自身ip
port = 6379 #一般redis都用这端口，不知道为啥
# 3、构建redis实例
r = redis.Redis(host=host,port=port,decode_responses=True)

# 测试
r.set('test, 'value', ex=10) # ex表示失效时间，以秒为单位
print(r.get('test')) 
```

#### 二、连接池+管道：
- **连接池作用**  
> redis-py使用connection pool来管理对一个redis server的所有连接，避免每次建立、释放连接的开销。默认，每个Redis实例都会维护一个自己的连接池。
可以直接建立一个连接池，然后作为参数Redis，这样就可以实现多个Redis实例共享一个连接池
- **管道作用**  
redis-py默认在执行每次请求都会创建（连接池申请连接）和断开（归还连接池）一次连接操作，如果想要在一次请求中指定多个命令，则可以使用pipline实现一次请求指定多个命令，并且默认情况下一次pipline 是原子性操作。
```
# -*- conding:utf-8 -*-
# 1、导入包
import redis

# 2、构建连接池pool
r_pool = redis.ConnectionPool(host, port,decode_responses=True)

# 3、构建redis实例，连接池pool作为参数
r = redis.Redis(connection_pool=r_pool)

# 4、设置管道pipeline
# pipe = r.pipeline(transaction=False)    # 默认的情况下，管道里执行的命令可以保证执行的原子性，执行pipe = r.pipeline(transaction=False)可以禁用这一特性。
# pipe = r.pipeline(transaction=True)
pipe = r.pipeline() # 创建一个管道

# 5、写入值及执行
pipe.set('name', 'jack')
pipe.set('role', 'sb')
res = pipe.execute()

print(res)
```
##### 其他函数、类型操作可参考：
https://www.jianshu.com/p/2639549bedc8
