/*
* Copyright 2001-2022 Artima, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import sbt.IO

import scala.io.Source
import java.io.{File, FileWriter, BufferedWriter}

object Generator {

  private def copyFile(sourceFile: File, destFile: File): File = {
    val destWriter = new BufferedWriter(new FileWriter(destFile))
    try {
      val lines = Source.fromFile(sourceFile).getLines.toList
      var skipMode = false
      for (line <- lines) {
        destWriter.write(line.replace("/*infix*/", "infix"))
        destWriter.newLine()
      }
      destFile
    }
    finally {
      destWriter.flush()
      destWriter.close()
      println("Copied " + destFile.getAbsolutePath)
    }
  }

  def generateWebBrowserForScala3(scalaV: String, targetDir: File): Seq[File] = {
    if (scalaV.startsWith("3.")) {
      targetDir.mkdirs()  
      val sourceFile = new File("src/main/scala-2/org/scalatestplus/selenium/WebBrowser.scala")
      val targetFile = new File(targetDir, "WebBrowser.scala")
      List(copyFile(sourceFile, targetFile))
    }
    else
      List.empty  
  }  

}