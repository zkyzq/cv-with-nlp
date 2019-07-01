
# [git doc](https://git-scm.com/book/zh/v2)
# [github帮助文档ch](https://github.com/waylau/github-help)

# 1. 新建git仓库

## 1. git用户信息配置
```
# 配置git全局设置，Git global setup
# 代码提交者信息，信息会显示在log里
git config --global user.name haomengyuan001
git config --global user.email haomengyuan001@ke.com
```

git配置的级别
directory | level | config sample
:---|:---:|:---:
/etc/gitconfig | system |
~/.gitconfig \ ~/.config/git/config | user | git config --global user.name haomengyuan001
.git/config | project | git config user.name haomengyuan001@ke.com

## 2. clone远程仓库,并创建新文件
```
# 克隆远程仓库
git clone git@git.lianjia.com:tupu/ics_triple_qa.git [ics_triple_qa]

# 创建新文件
cd ics_triple_qa
touch README.md

# 向远程仓库提交该更新
git add README.md
git commit -m "add README"
git push -u origin master
# -u --set-upstream
```
## 3 本地创建项目，保存至远程仓库
```
cd existing_folder

# 初始化
git init

# 添加远程仓库，配置信息
git remote add origin git@git.lianjia.com:tupu/ics_triple_qa.git

git add -A # 将全部文件加入暂存区
git commit -m "Initial commit"
git push -u origin master
```
## 4 modify:远端git仓库

```
cd existing_repo

# Rename
git remote rename <old> <new> # 修改远程仓库名

# Add
git remote add <name> <url> # 新增远程仓库

# Modify
1. 修改远程仓库地址
git remote set-url origin [url]
2. 修改：先删后加(已验证)
git remote rm origin
git remote add origin [url]
3. 直接修改config文件
vim .git/config

# Rm
git remote rm origin
```


# 2. clone
## 1. 递归clone
假设在git项目A中，A又包含了其他的git项目B。

如果在克隆A时，也同时想把B的内容一起clone下来。需要使用参数 --recursive，即：

`git clone --recursive [url]`

## 2. 指定分支clone
`git clone -b branch_name address [target_folder_name]`


# 3. 仓库分支：branch

[git branch用法总结，查看、新建、删除、重命名](https://blog.csdn.net/afei__/article/details/51567155)

## 1. Delete
```
# Delete remote branch
git push origin :remote-branch-name
git branch (-d | -D) [-r] <branchname> # -r: remote; D强制; d完全commit后可删除
```

## 2. look up
```
git branch [--list] # 查看本地分支
git branah [-r| -a]   # 远程/全部分支
```
## 3. 重命名
```
git branch (-m | -M) [<oldbranch>] <newbranch>
# -m, --move: Move/rename a branch and the corresponding reflog.
# -M: 强制，Move/rename a branch even if the new branch name already exists.
```

## 4. 获取远程分支
```
1.git checkout -b 本地分支名x origin/远程分支名x
# 使用该方式会在本地新建分支x，并自动切换到该本地分支x
# 采用此种方法建立的本地分支会和远程分支建立映射关系

2. 分两步
git fetch --all # 取远程的分支到本地
git checkout [branch_name]

3. 借助fetch
git fetch origin master:master1
```


- [git看不到别人创建的远程分支](https://blog.csdn.net/qq_25283709/article/details/78290277)
```
“git fetch [remote-name]”
# 这个命令会访问远程仓库，从中拉取所有你还没有的数据。 执行完成后，你将会拥有那个远程仓库中所有分支的引用，可以随时合并或查看
```

## 5. git clone / git pull / git fetch作用与区别
```
git clone:克隆，本地没有repository，从远程仓库复制下载  
git pull: 拉，本地有repository，从远程仓库将commit数据下载，并与本地代码进行merge  
git fetch:取，本地有repository，从远程仓库将数据拉到本地，但不进行merge，git pull = git fetch + git merge
```

## 6. 合并
[远程仓库获取最新代码合并到本地分支](https://blog.csdn.net/hanchao5272/article/details/79162130)

```
1. 直接合并远端的代码，无法提前处理冲突
git pull origin master

2. 建立本地额外分支
git fetch origin master:master1 # 获取最新代码到本地临时分支
git diff master1 # 查看版本差异
git merge master1 # 合并最新分支到本地分支
git branch -D master # 删除无用分支

3. 不建立额外分支
git fetch origin master # 先获取
git log -p master..origin/master # 对比
git merge origin/master # 合并
```

# 6. stash 临时保存工作区的内容
pull\merge等操作，发现和本地未保存的内容有冲突，stash派上用场了。

but `要慎用`。

[使用git stash命令保存和恢复进度](https://blog.csdn.net/daguanjia11/article/details/73810577) 这里的中文解释更加详细

```
git stash  # 保存当前工作区的内容
git stash list  # 保存进度点列表
git stash show -p stash@{0} # 查看某次stash所修改的差异
git stash pop stash@{0}  # 恢复到某进度
git stash drop stash@{0} # 删除某次stash的内容
git stash clear # 删除所有缓存内容
```
- 保存当前工作进度，会把暂存区和工作区的改动保存起来。
- 执行完这个命令后，在运行git status命令，就会发现当前是一个干净的工作区，没有任何改动。
- 使用git stash save 'message...'可以添加一些注释

# 8. push / pull [覆盖]
## 1. 覆盖远端
```
git push origin <分支名> --force
# 覆盖掉远端的版本信息，使远端的仓库也回退到相应的版本，需要加上参数--force
```
## 2. 覆盖本地
```
git fetch --all
git reset --hard origin/master
# 从远端master分支获得代码，覆盖本地工作区、暂存区，head变为远端
git pull
```

## 3. 指定分支-本地推入远端
`git push origin 本地分支名:远程分支名`


# 9. 撤销操作

[git 撤销修改-廖雪峰: 圆称之为最强](https://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/001374831943254ee90db11b13d4ba9a73b9047f4fb968d000)

[花式撤销](https://www.jb51.net/article/93563.htm) 几乎可以撤销所有操作，需要仔细阅读

[git放弃本地修改](https://www.cnblogs.com/qufanblog/p/7606105.html)

## 1. 撤销修改
```
# 1. 丢弃工作区的修改
git checkout -- readme.txt
# 将工作区文件恢复到 暂存/版本库 记录的内容

# 2. 放弃暂存区的修改
git reset HEAD <file>
git reset HEAD . # 如果是撤销所有的已经add的文件
git reset HEAD -filename # 如果是撤销某个文件或文件夹

# 3. 执行完commit, 想撤回：
git reset --soft HEAD^ # 只是撤回commit,代码会做保留
# HEAD^=HEAD~1 上一个版本、HEAD~2 上两个版本

# --mixed：不删除工作空间改动代码，撤销commit，并且撤销git add，为默认参数
# --soft：不删除工作空间改动代码，撤销commit，不撤销git add
# --hard：删除工作空间改动代码，撤销commit，撤销git add。注意完成这个操作后，就恢复到了上一次的commit状态。
# commit注释写错，只改注释：git commit --amend
```

## 2. 放弃某文件的追踪

[删除但保留本地文件](https://help.github.com/en/articles/ignoring-files)

If you already have a file checked in, and you want to ignore it, Git will not ignore the file if you add a rule later. In those cases, you must untrack the file first, by running the following command in your terminal:
```
git rm --cached FILENAME
```

# 10. git配置
## 1. 显示颜色配置
[git显示颜色配置](https://blog.csdn.net/furzoom/article/details/51278232)

# 11. git tag

[廖雪峰-标签管理](https://www.liaoxuefeng.com/wiki/896043488029600/900788941487552)

[Git查看、删除、重命名远程分支和tag](https://blog.zengrong.net/post/1746.html)
x

# 17. 对比两次commit的不同
[两次commit的差异](https://blog.csdn.net/u012830148/article/details/77497240)

# 18. git忽略文件
[Git忽略文件.gitignore的使用](https://www.jianshu.com/p/a09a9b40ad20)

[官方示例](https://github.com/github/gitignore/tree/master/Global)
