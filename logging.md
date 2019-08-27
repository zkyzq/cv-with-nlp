- **功能**   
python的一个模块，主要用于输出运行日志，可以设置输出日志的等级、日志保存路径、日志文件回滚等，说白了就是高级print，高级在以下两点：
  - 可以规定不同的日志等级输出信息，在调试时可以输出较多信息而在release版本中可以只输出重要信息
  - 设置输出路径及如何输出，而print只能输出到标准输出中，这样可能会干扰
- **日志等级**  
**DEBUG**	详细信息，典型地调试问题时会感兴趣。 详细的debug信息。  
**INFO**	证明事情按预期工作。 关键事件。  
**WARNING**	表明发生了一些意外，或者不久的将来会发生问题（如‘磁盘满了’）。软件还是在正常工作。  
**ERROR**	由于更严重的问题，软件已不能执行一些功能了。 一般错误消息。  
**CRITICAL**	严重错误，表明软件已不能继续运行了。  
**NOTICE**	不是错误，但是可能需要处理。普通但是重要的事件。  
**ALERT**	需要立即修复，例如系统数据库损坏。  
**EMERGENCY**	紧急情况，系统不可用（例如系统崩溃），一般会通知所有用户。
***注***
上面的日志等级是从上到下依次升高的，即：DEBUG < INFO < WARNING < ERROR < CRITICAL，而日志的信息量是依次减少的；
当为某个应用程序指定一个日志级别后，应用程序会记录所有日志级别大于或等于指定日志级别的日志信息，而不是仅仅记录指定级别的日志信息，nginx、php等应用程序以及这里的python的logging模块都是这样的。同样，logging模块也可以指定日志记录器的日志级别，只有级别大于或等于该指定日志级别的日志记录才会被输出，小于该等级的日志记录将会被丢弃。
- **常规使用方案**  
开发应用程序或部署开发环境时，可以使用DEBUG或INFO级别的日志获取尽可能详细的日志信息来进行开发或部署调试；  
应用上线或部署生产环境时，应该使用WARNING或ERROR或CRITICAL级别的日志来降低机器的I/O压力和提高获取错误日志信息的效率。日志级别的指定通常都是在应用程序的配置文件中进行指定的。
- **两种记录日志方式：**  
1、使用logging提供的模块级别的函数  
2、使用logging的四大组件：loggers、handlers、filters、formatters
- **方式一：模块级别函数**：  
最直接的：
```
>>> logging.debug("Debug")
>>> logging.info("info")
>>> logging.warning("warning")
WARNING:root:warning
>>> logging.error("Error")
ERROR:root:Error
>>> logging.critical("Critical")
CRITICAL:root:Critical
```
> 由以上示例可知，warning以上的可以输出，但是debug和info级别的无输出。这是因为logging模块提供的日志记录函数所使用的日志器设置的日志级别是WARNING，因此只有WARNING级别的日志记录以及大于它的ERROR和CRITICAL级别的日志记录被输出了，而小于它的DEBUG和INFO级别的日志记录被丢弃了。 

> 解决这个问题要设置logging的等级level，还要先进行配置logging.basicConfig。

```
>>> import logging
>>> logging.basicConfig(filename='logger.log', level=logging.INFO)
>>> logging.debug("Debug")
>>> logging.info("info")
>>> logging.warning("warning")
>>> logging.error("Error")
>>> logging.critical("Critical")

# logger.log 中的输出结果,可以看到info及以上的都记录了下来，低级别的debug没有输出
'''
INFO:root:info
WARNING:root:warning
ERROR:root:Error
CRITICAL:root:Critical
'''
```

logging.basicConfig的参数
