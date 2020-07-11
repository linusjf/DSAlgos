evaluate(new File("properties.groovy"))
import java.nio.file.Files
import java.nio.file.StandardCopyOption  
import java.nio.file.Paths

def renameSuppressions() {
  def props = new properties()
  props = props.getProperties()
    def supprFileName = 
      props.getProperty(
        "checkstyle.suppressionsFile")
  def suppr = Paths.get("${project.name}-xpath.xml")
    def target = null
    if (Files.exists(suppr)) {
      def supprNew = Paths.get(supprFileName)
        target = Files.move(suppr, supprNew)
        println "Renamed ${project.name}-xpath.xml to " + supprFileName
    }
  return target
}

renameSuppressions()
