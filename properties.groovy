import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.apache.maven.project.MavenProject

Properties getProjectProperties() {
def pomFile = "pom.xml"
Model model = null
FileReader reader = null
MavenXpp3Reader mavenreader = new MavenXpp3Reader()
try {
    file = new File(pomFile)
    reader = new FileReader(file)
    model = mavenreader.read(reader)
    model.setPomFile(file)
} catch(Exception ex){
  System.err.println(ex.getMessage())
}
MavenProject project = new MavenProject(model)
return project.getProperties()
}
