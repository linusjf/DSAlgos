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

def cp = getClassPath(this.class.classLoader, 
    new StringBuilder())

  def props = new properties()
  props = props.getProjectProperties()
    def csMainClass = 
      props.getProperty(
        "cs.main.class")
    def csRules = 
      props.getProperty(
        "checkstyle.rules")
    def csProps = 
      props.getProperty(
        "checkstyle.properties")
String[] args = ["java", "-cp", cp,
    csMainClass,
    "-c", csRules,
"-p", csProps,
"-o", "${project.name}-xpath.xml",
"-g", "${basedir}/src"]

ProcessBuilder pb = new ProcessBuilder(args)
pb = pb.inheritIO()
Process proc = pb.start();
proc.waitFor()
