import java.nio.file.Paths
import java.nio.file.Files

def getClassPath(classLoader, sb) {
  for (url in classLoader.getURLs()) {
     sb.append(url.getFile().toString())
     .append(':')
    }
  if (classLoader.parent) {
     getClassPath(classLoader.parent, sb)
  }
  return sb.toString()
}

def getXmlFiles() {
 File baseDir = project.basedir
 FilenameFilter fileNameFilter = 
 { 
   dir,name -> 
   name.toLowerCase().endsWith(".xml") || 
   name.toLowerCase().endsWith(".pom")
 }
 String[] xmlFiles = 
 baseDir.listFiles(fileNameFilter)
 StringBuilder sb = new StringBuilder()
 for (file in xmlFiles)
   sb.append(file).append(",")
 sb.deleteCharAt(sb.length() - 1)
 return sb.toString()
}

def pmdMainClass = 
      project.properties["pmd.main.class"]
def pmdRules = 
        project.properties["pmd.xml.rules"]

def classLoader = Class.forName(pmdMainClass).getClassLoader()
def cp = getClassPath(classLoader, 
    new StringBuilder())

String xmlFiles = getXmlFiles()

PrintStream o = new PrintStream("filelist.txt"); 
PrintStream console = System.out; 
System.setOut(o); 
System.out.print(xmlFiles); 
System.setOut(console);

Files.createDirectories(Paths.get("target"));

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
  "target/pmdxml.txt",
  "-l", 
  "xml",
"-f", 
  "text",
  "-filelist", 
  "filelist.txt",
"-V"]

ProcessBuilder pb = new ProcessBuilder(args)
pb = pb.inheritIO()
Process proc = pb.start();
proc.waitFor()
