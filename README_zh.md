# 扫雷

使用Swing实现的经典扫雷游戏。具有多种难度级别和历史记录的时间跟踪。

## 功能

- 多种难度级别：简单、中等、困难、自定义
- 每局游戏的时间跟踪
- 历史记录保存
- 胜负检测
- 直观的GUI界面
- 记录清除功能

## 要求

- Java开发工具包（JDK）8或更高版本

## 安装和运行

1. 确保系统上安装了JDK。
2. 导航到项目目录。
3. 编译源代码：
   ```
   javac -d bin src/com/minesweeper/game/*.java
   
   xcopy /E /I resources bin(Windows)
   or
   mkdir -p bin
   cp -R resources bin/ (Mac)
   
   ```
4. 运行游戏：
   ```
   java -cp bin com.minesweeper.game.MainGame
   ```

## 如何玩

1. 选择难度级别
2. 点击"START"开始
3. 左键点击揭示单元格
4. 右键点击标记潜在地雷
5. 避免点击地雷
6. 揭示所有非地雷单元格获胜

## 项目结构

- `src/com/minesweeper/game/MainGame.java`：主入口点和游戏初始化
- `src/com/minesweeper/game/BeginUI.java`：难度选择和菜单界面
- `src/com/minesweeper/game/GameUI.java`：主游戏板界面
- `src/com/minesweeper/game/SingleGrid.java`：单个网格单元格组件
- `src/com/minesweeper/game/MessageDialogs.java`：对话框工具
- `src/com/minesweeper/game/Number.java`：数字显示工具
- `HISTORY`：存储游戏记录的文件
- `bin/`：编译的类文件

## 数据存储

游戏记录使用对象序列化存储在`HISTORY`文件中。

## 贡献

欢迎贡献！请提交问题和拉取请求以进行增强。
