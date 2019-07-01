
## 1. 统计某字符的出现
假设你要统计的字符为"test",现在输入

`:%s/test/&/gn(回车)`

## 2. 两个文件逐行比较

[技巧：Vimdiff 使用](https://www.ibm.com/developerworks/cn/linux/l-vimdiff/index.html)

[vimdiff 配色修改](https://oomake.com/question/409686)
```
echo $TERM # xterm-256color
export TERM=xterm-16color
vimdiff <file1> <file2>
:highlight
```

## 3. 指定行替换某字符
[替换选中行的文本](https://segmentfault.com/q/1010000002552573)

## 4. vim 多行复制、删除
[blog](https://blog.csdn.net/xiyuan1999/article/details/5680102)

vi复制多行文本的方法
- 方法1：
```
光标放到第6行，输入：2yy
光标放到第9行，输入：p
# 此方法适合复制少量行文本的情况，复制第6行（包括）下面的2行数据，放到第9行下面。
```

- 方法2：
```
命令行模式下输入: 6,9 co 12
# 复制第6行到第9行之间的内容到第12行后面。
```
- 方法3：
有时候不想费劲看多少行或复制大量行时，可以使用标签来替代
```
光标移到起始行，输入ma
光标移到结束行，输入mb
光标移到粘贴行，输入mc
然后 :'a,'b co 'c   把 co 改成 m 就成剪切了
```

`要删除多行的话，可以用 ：5, 9 de`

## 4 快速移动至行首或行尾 
命令行模式下"$"和"^"

## 5 查找关键字： 
命令行模式下“/关键字”
