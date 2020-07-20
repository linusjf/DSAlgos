import java.nio.file.Files
import java.nio.file.StandardCopyOption  
import java.nio.file.Paths

def backupSuppressions() {
  def supprFileName = 
      project.properties["checkstyle.suppressionsFile"]
  def suppr = Paths.get(supprFileName)
  def target = null
  if (Files.exists(suppr)) {
    def supprBak = Paths.get(supprFileName + ".bak")
    target = Files.move(suppr, supprBak,
        StandardCopyOption.REPLACE_EXISTING)
    println "Backed up " + supprFileName
  }
  return target
}

def renameSuppressions() {
  def supprFileName = 
      project.properties["checkstyle.suppressionsFile"]
  def suppr = Paths.get(project.name + "-xpath.xml")
  def target = null
  if (Files.exists(suppr)) {
    def supprNew = Paths.get(supprFileName)
    target = Files.move(suppr, supprNew)
    println "Renamed " + suppr + " to " + supprFileName
  }
  return target
}

def getClassPath(classLoader, sb) {
  classLoader.getURLs().each {url->
     sb.append("${url.getFile().toString()}")
       .append(System.getProperty("path.separator"))
  }
  if (classLoader.parent) {
     getClassPath(classLoader.parent, sb)
  }
  return sb.toString()
}

backupSuppressions()

def cp = getClassPath(this.class.classLoader, 
    new StringBuilder())
def csMainClass = 
      project.properties["cs.main.class"]
def csRules = 
      project.properties["checkstyle.rules"]
def csProps = 
      project.properties["checkstyle.properties"]

String[] args = ["java", "-cp", cp,
    csMainClass,
    "-c", csRules,
"-p", csProps,
"-o", project.name + "-xpath.xml",
"-g", "src"]

ProcessBuilder pb = new ProcessBuilder(args)
pb = pb.inheritIO()
Process proc = pb.start();
proc.waitFor()

renameSuppressions()
