evaluate(new File("properties.groovy"))

import java.nio.file.Paths
import java.nio.file.Files

def getClassPath(classLoader, sb) {
  classLoader.getURLs().each {url->
     sb.append("${url.getFile().toString()}:")
  }
  if (classLoader.parent) {
     getClassPath(classLoader.parent, sb)
  }
  return sb.toString()
}

def getXmlFiles() {
 File baseDir = new File("${basedir}")
 FilenameFilter fileNameFilter = 
 { 
   dir,name -> 
   name.toLowerCase().endsWith(".xml") || 
   name.toLowerCase().endsWith(".pom")
 }
 String[] xmlFiles = 
 basedir.listFiles(fileNameFilter)
 StringBuilder sb = new StringBuilder()
 for (file in xmlFiles)
   sb.append(file).append(",")
 sb.deleteCharAt(sb.length() - 1)
 return sb.toString()
}

def cp = getClassPath(this.class.classLoader, 
    new StringBuilder())

  def props = new properties()
  props = props.getProperties()
    def pmdMainClass = 
      props.getProperty(
        "pmd.main.class")
    def pmdRules = 
      props.getProperty(
        "pmd.xml.rules")

  String xmlFiles = getXmlFiles()

  PrintStream o = new PrintStream(new File("${basedir}/filelist.txt")); 
  PrintStream console = System.out; 
  System.setOut(o); 
  System.out.print(xmlFiles); 
  System.setOut(console);

  Files.createDirectories(Paths.get("${basedir}/target"));

String[] args = ["java", 
  "-cp", 
  cp,
    pmdMainClass,
    "-R", 
    pmdRules,
"-cache", 
  "pmdxml.cache",
  "-failOnViolation", 
  "false",
  "-r", 
  "${basedir}/target/pmdxml.txt",
  "-l", 
  "xml",
"-f", 
  "text",
  "-filelist", 
  "${basedir}/filelist.txt",
"-V"]

ProcessBuilder pb = new ProcessBuilder(args)
pb = pb.inheritIO()
Process proc = pb.start();
proc.waitFor()
