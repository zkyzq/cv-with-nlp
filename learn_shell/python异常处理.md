## try机制
### 基本功能
尝试执行try后边的语句，如果有错误则执行except后边的语句；在try或者except语句执行后，最后执行finally语句（可以不加finally程序段）
### 工作原理
try的工作原理是，当开始一个try语句后，python就在当前程序的上下文中作标记，这样当异常出现时就可以回到这里，try子句先执行，接下来会发生什么依赖于执行时是否出现异常。

- 如果当try后的语句执行时发生异常，python就跳回到try并执行第一个匹配该异常的except子句，异常处理完毕，控制流就通过整个try语句（除非在处理异常时又引发新的异常）。
- 如果在try后的语句里发生了异常，却没有匹配的except子句，异常将被递交到上层的try，或者到程序的最上层（这样将结束程序，并打印缺省的出错信息）。
- 如果在try子句执行时没有发生异常，python将执行else语句后的语句（如果有else的话），然后控制流通过整个try语句。
- 示例
```
try:
    print('try...'')
    r = 10 / 0
    print('result:', r)
except ZeroDivisionError as e:
    print('except:', e)
finally:
    print('finally...')
print('END')
# 输出：
# try...
# except: division by zero
# finally...
# END
```
### 多种异常
可以多个except罗列，也可以在一行内完成
- 示例1
```
try:
    print('try...')
    r = 10 / int('2')
    print('result:', r)
except ValueError as e:
    print('ValueError:', e)
except ZeroDivisionError as e:
    print('ZeroDivisionError:', e)
else:
    print('no error!')
finally:
    print('finally...')
print('END')
```
- 示例2:
```
try:
    print('try...')
    r = 10 / int('2')
    print('result:', r)
except (ValueError, ZeroDivisionError) as e:
    print('Error:', e)
else:
    print('no error!')
finally:
    print('finally...')
print('END')
```
### 不带任何异常类型
可以捕获所有异常，但不能通过该程序识别出具体的异常信息
- 示例：
```
try:
    print('try...')
    r = 10 / int('2')
    print('result:', r)
except:
    print('Error:', e)
else:
    print('no error!')
finally:
    print('finally...')
print('END')
```
### 自己触发异常raise
自己的理解是在某种地方把某种情况设定为异常（可能对于python解释器来说本身来说这并不算是异常），然后可以结合except向外抛出。  
***注：*** 如果后边没有except，raise语句产生异常后的***本部分代码***（***而不是后边所有代码***）不会再执行，如果有except的话，会把except的内容执行完然后停止执行  
- 格式
```
raise [Exception [, args [, traceback]]]
```
其中Exception是关键字，表示python内置异常的一种，args表示故障参数
- 示例
```
def test(level):
    if level < 10:
        raise Exception("Invalid level !!!!!!",level)
        print('Whether the oder after raise is performed or not.') # 这句话没有任何用，任何情况下都不会被执行
    else:
        print('end of test()')

# 不加except语句
def main_1(level1):
        test(level1)
        print('main1: without except sentence')
    
# 加上except语句,测试1：raise是否
def main_2(level2):
    try:
        test(level2)
        print('main2: 2222222222')
    except Exception as err:
        print('main2: ', err)
    finally:
        print('END of ALL')

level1 = 10
level2 = 10
main_1(level1)
main_2(level2)

'''level1=10，level2=10的正常结果
end of test()
main1: without except sentence
end of test()
main2: 2222222222
END of ALL
'''

'''level1=10，level2=5的异常结果,可以看到main_1正常运行，而main_2抛出异常及异常值('Invalid level !!!!!!', 5)
Normal end of test()
main1: without except sentence
main2:  ('Invalid level !!!!!!', 5)
END of ALL
'''

'''level1=5，level2=5的异常结果，可以看到main_1因为没有except语句，所以只是异常被提出，而且被追踪，但不会继续运行
Traceback (most recent call last):
  File ".\ErrorProcess.py", line 24, in <module>
    main_1()
  File ".\ErrorProcess.py", line 11, in main_1
    test(5)
  File ".\ErrorProcess.py", line 4, in test
    raise Exception("Invalid level !!!!!!",level)
Exception: ('Invalid level !!!!!!', 5)
'''

'''level1=10，level2=5，且先运行main_2，再运行main_1,异常结果如下，main_2抛出异常,且抛出后main_1继续运行
main2:  ('Invalid level !!!!!!', 5)
END of ALL
Normal end of test()
main1: without except sentence
'''
```
## 调试
### print
都懂，简单粗暴，麻烦，还需删除或屏蔽
### assert断言
可以替换print，优点在于最后不用屏蔽语句，可以使用**python -O test.py**来实现assert语句的屏蔽
- 示例：
```
def foo(s):
    n = int(s)
    assert n != 0, 'n is zero!'
    return 10 / n

def main():
    foo('0')
if __name__ == '__main__':
     main()
# 输出  
'''
Traceback (most recent call last):
  File "test.py", line 10, in <module>
    main()
  File "test.py", line 8, in main
    foo('0')
  File "test.py", line 4, in foo
    assert n != 0, 'n is zero!'
AssertionError: n is zero!
'''
```
### logging
允许你指定记录信息的级别，有debug，info，warning，error等几个级别，当我们指定level=INFO时，logging.debug就不起作用了。同理，指定level=WARNING后，debug和info就不起作用了。这样一来，你可以放心地输出不同级别的信息，也不用删除，最后统一控制输出哪个级别的信息。 

logging的另一个好处是通过简单的配置，一条语句可以同时输出到不同的地方，比如console和文件。
- 示例：
```
# -*- coding:utf-8 -*-
import logging
logging.basicConfig(level = logging.INFO) # 配置分级，包括DEBUG、INFO、WARNING、ERROR等

def foo(s):
    n = int(s)
    logging.info('n = %d'%n)
    return 10 / n
  
def main():
    foo('0')
if __name__ == '__main__':
    main()
# 传入1时输出：
# INFO:root:n = 1
#传入0时输出：
'''
INFO:root:n = 0
Traceback (most recent call last):
  File "test.py", line 13, in <module>
    main()
  File "test.py", line 11, in main
    foo('0')
  File "test.py", line 8, in foo
    return 10 / n
ZeroDivisionError: integer division or modulo by zero
'''
```
