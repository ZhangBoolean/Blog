
File()是在编写程序时遇到频率相当高的东西，下面就简单从我日常最长用到的几个方面简单介绍一下，以后可能会有继续的边用边补充。

* 创建文件

   提到创建文件，很多人第一反应想到的就是File file = new File()，不过需要明确的是，这里的file并没有被创建，而只是创建了
一个File对象。


     File dayFile = new File(equiFile, dayStr);
     if(!dayFile.exists())
     {
         dayFile.mkdir();
     }

创建之后需要检查文件路径是否有创建成功，如果没有创建成功，则需要使用mkdir()或者mkdirs()重新创建路径。


* new File()的参数

new File()里的参数，这里可以是一个参数也可以是两个参数，一个参数时传递的是创建的file的路径字符串，比如File file = new File("E:\\test\\a")；
两个参数时，第一个参数也是文件的路径，第二个参数则是文件的名字，例如File dayFile = new File(equiFile, dayStr);，当然，如果创建的时一个多级目
录，第二个参数也可以是新的文件夹的名称，也就是子目录的名称


     File equiFile = new File(E:\\test\\a, equiNum + equiChal);    
        //File f = new File(directory, filename);
        if(!equiFile.exists()){
            equiFile.mkdirs();
        }

* mkdir和createNewFile

再来说说mkdir和createNewFile的区别，mkdir是创建文件夹，createNewFile是创建文件，并且createNewFile需要抛出异常。它们都是返回一个Boolean类
型的值。

        File dayFile = new File(equiFile, dayStr);
        if(!dayFile.exists())
            dayFile.mkdir();

        File timeFile = new File(dayFile, timeStr + ".mp4");      
        try {
            timeFile.createNewFile();       //createNewFile返回一个boolean类型的值
        } catch (IOException e) {
            e.printStackTrace();
        }

* getPath和getAbsolutePath


  getPath得到的是构造参数的路径。也就是说，new File()括号的里参数总是getPath返回的路径值。
  getAbsolutePath得到的是全路径。如果构造参数是相对路径，则返回当前目录的绝对路径+构造参数路径；如果是绝对路径则直接返回绝对路径。即不管括号里的参
  数值是从哪里开始的 ，这里返回的值总是从根目录开始的文件存储的绝对路径。


