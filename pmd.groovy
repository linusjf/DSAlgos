evaluate(new File("properties.groovy"))

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
 FilenameFilter fileNameFilter = { dir,name -> name.toLowerCase().endsWith(".xml")}
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
  props = props.getProjectProperties()
    def pmdMainClass = 
      props.getProperty(
        "pmd.main.class")
    def pmdRules = 
      props.getProperty(
        "pmd.xml.rules")

  String xmlFiles = getXmlFiles()

  PrintStream o = new PrintStream(new File("${basedir}/filelist.txt")); 
  // Store current System.out before assigning a new value 
  PrintStream console = System.out; 
  // Assign o to output stream
  System.setOut(o); 
  System.out.print(xmlFiles); 
  // Use stored value for output stream 
  System.setOut(console);

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
  "${basedir}/pmdxml.txt",
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
