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

String[] args = ["java", "-cp", cp,
    "com.puppycrawl.tools.checkstyle.Main",
    "-c", "checkstyle.xml",
"-p", "checkstyle.properties",
"-o", "${project.name}-xpath.xml",
"-g", "${basedir}/src"]

ProcessBuilder pb = new ProcessBuilder(args)
pb = pb.inheritIO()
Process proc = pb.start();
proc.waitFor()
