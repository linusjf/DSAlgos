import java.nio.file.Files
import java.nio.file.StandardCopyOption  
import java.nio.file.Paths

def renameSuppressions() {
  def suppr = Paths.get("${project.name}-xpath.xml")
    def target = null
    if (Files.exists(suppr)) {
      def supprNew = Paths.get("suppressions-xpath.xml")
        target = Files.move(suppr, supprNew)
        println "Renamed ${project.name}-xpath.xml to suppressions-xpath.xml"
    }
  return target
}

renameSuppressions()
