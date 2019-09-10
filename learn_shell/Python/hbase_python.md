> 深入浅出原理：    https://www.ibm.com/developerworks/cn/analytics/library/ba-cn-bigdata-hbase/index.html  

#### 存储层级：
RowKey---CF(ColumnFamily)---CK(ColumnKey/Qualifier)---value
#### 依赖库：
```
# 新版thrift2
from thrift.transport import TTransport
from thrift.transport import TSocket
from thrift.transport import THttpClient
from thrift.protocol import TBinaryProtocol, TCompactProtocol

from hbase import THBaseService
from hbase.ttypes import TPut, TColumnValue, TGet
```
#### 存入单个值及查询单个值
> https://msd.misuland.com/pd/3127746505234974136

```
from thrift.transport import TSocket
from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport
from hbase import THBaseService

from hbase.ttypes import TPut, TColumnValue, TGet

transport = TTransport.TBufferedTransport(TSocket.TSocket('127.0.0.1', 9090))
protocol = TBinaryProtocol.TBinaryProtocolAccelerated(transport)
# 使用 client 实例进行操作
client = THBaseService.Client(protocol)
transport.open()

# 单个RowKey-CF-CK-value存入
tput = TPut(
    row='sys.cpu.user:20180421:192.168.1.1',
    columnValues=[
        TColumnValue(family='cf', qualifier='1015', value='0.28'),
    ]
)
client.put('tsdata', tput)

# 单个RowKey查询
tget = TGet(row='sys.cpu.user:20180421:192.168.1.1')
tresult = client.get('tsdata', tget)
for col in tresult.columnValues:
    print(col.qualifier, '=', col.value)
transport.close()
```
