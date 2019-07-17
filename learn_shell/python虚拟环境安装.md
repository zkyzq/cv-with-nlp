## virtualenv
每个工程所需环境不同，不可能全部全局配置，因此需要进行虚拟环境的配置，运行环境相互隔离。
- 配置步骤  
1.创建独立的运行环境  
```
virtualenv --no-site-packages venv 
# --no-site-packages表示表示系统中的python包都不会被复制过来,新建的Python环境被放到当前目录下的venv目录。
```
2.使用source进入当前环境
```
source venv/bin/activate
```
3.在虚拟环境中安装所需包
```
pip install -r requirements.txt
# 需要安装的包都在requirments中，每个占一行
```
4.退出虚拟环境
```
deactivate
# 完成所有程序后需要退出虚拟环境
```
