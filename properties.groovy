import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.project.MavenProject
import java.util.Properties

Properties getProperties() {
  Properties props = null      
  try {
    File file = new File("pom.xml")
    FileReader reader = new FileReader(file)
    MavenXpp3Reader mavenreader = new MavenXpp3Reader()
    Model model = mavenreader.read(reader)
    model.setPomFile(file)
    MavenProject project = new MavenProject(model)
    props = project.getProperties()
  } catch(Exception ex) {
    System.err.println(ex.getMessage())
  }
  return props
}
