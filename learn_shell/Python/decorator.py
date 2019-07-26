def funA(fn):
    print('A')
    fn() # 执行传入的fn参数
    return 'asd'
'''
下面装饰效果相当于：funA(funB)，
funB将会替换（装饰）成该语句的返回值；
由于funA()函数返回asd，因此funB就是asd
'''
@funA # 这样叫funB被funA修饰
def funB():
    print('B')
print(funB) # fkit
'''
输出：
A
B
asd
funB执行过程分成两步：
（1）执行funA(funB):先打印出'A'，然后fn()相当于funB()，打印出'B'，返回'asd'
（2）funB被funA()返回的字符串'asd'代替，所以打印出了'asd'
'''

# 更复杂情况！！！！同时说明其作用
def foo(fn):
    # 定义一个嵌套函数
    def bar(*args):
        print("===1===", args)
        n = args[0]
        print("===2===", n * (n - 1))
        # 查看传给foo函数的fn函数
        print(fn.__name__)
        fn(n * (n - 1))
        print("*" * 15)
        return fn(n * (n - 1))
    return bar
'''
下面装饰效果相当于：foo(my_test)，
my_test将会替换（装饰）成该语句的返回值；
由于foo()函数返回bar函数，因此funB就是bar
'''
@foo
def my_test(a):
    print("==my_test函数==", a)
# 打印my_test函数，将看到实际上是bar函数
print(my_test) # <function foo.<locals>.bar at 0x00000000021FABF8>
# 下面代码看上去是调用my_test()，其实是调用bar()函数
my_test(10)
my_test(6, 5)
'''输出
<function foo.<locals>.bar at 0x0000014D9FDD7B70>
===1=== (10,)
===2=== 90
my_test
==my_test函数== 90
***************
==my_test函数== 90
===1=== (6, 5)
===2=== 30
my_test
==my_test函数== 30
***************
==my_test函数== 30


解释：
上面程序定义了一个装饰器函数 foo，该函数执行完成后并不是返回普通值，
而是返回 bar 函数（这是关键），这意味着被该＠foo修饰的函数最终都会被替换成bar函数。

上面程序使用 ＠foo 修饰 my_test() 函数，因此程序同样会执行 foo(my_test)，
并将 my_test 替换成 foo() 函数的返回值：bar 函数。所以，上面程序在打印 my_test 函数时（print(my_test)），
实际上输出的是 bar 函数，这说明 my_test 已经被替换成 bar 函数。
接下来程序两次调用my_test() 函数，实际上就是调用 bar() 函数。

！！！！！！函数修饰器作用！！！！！！！！
以在被修饰函数的前面添加一些额外的处理逻辑（比如权限检查），
也可以在被修饰函数的后面添加一些额外的处理逻辑（比如记录日志），
还可以在目标方法抛出异常时进行一些修复操作……
这种改变不需要修改被修饰函数的代码，只要增加一个修饰即可。
'''
# 权限检查例子：
def auth(fn):
    def auth_fn(*args):
        # 用一条语句模拟执行权限检查
        print("----模拟执行权限检查----")
        # 回调要装饰的目标函数
        fn(*args)
    return auth_fn
@auth
def test(a, b):
    print("执行test函数,参数a: %s, 参数b: %s" % (a, b))
# 调用test()函数,其实是调用装饰后返回的auth_fn函数
test(20, 15)
test(2,3)
'''
输出：
----模拟执行权限检查----
执行test函数,参数a: 20, 参数b: 15
----模拟执行权限检查----
执行test函数,参数a: 2, 参数b: 3

解释：
相当于在执行test函数前，添加了auth_fn()进行权限检查，而不需要改变test的任何代码
'''
