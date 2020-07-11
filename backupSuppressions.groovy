evaluate(new File("properties.groovy"))

import java.nio.file.Files
import java.nio.file.StandardCopyOption  
import java.nio.file.Paths
              
def backupSuppressions() {
  def props = new properties()
  props = props.getProperties()
    def supprFileName = 
      props.getProperty(
        "checkstyle.suppressionsFile")
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

backupSuppressions()
