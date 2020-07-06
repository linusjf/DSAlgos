import java.nio.file.Files
import java.nio.file.StandardCopyOption  
import java.nio.file.Paths
              
def backupSuppressions() {
  def suppr = Paths.get("suppressions-xpath.xml")
    def target = null
    if (Files.exists(suppr)) {
      def supprBak = Paths.get("suppressions-xpath.xml.bak")
        target = Files.move(suppr, supprBak,
            StandardCopyOption.REPLACE_EXISTING)
        println "Backed up suppressions-xpath.xml"
    }
  return target
}

backupSuppressions()
