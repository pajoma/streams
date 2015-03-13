# Introduction #

This wiki will describe how to translate WSML ontologies into an RDF/XML or N3 serialization by using the wsmo4j-rdf-extension API.


# How to proceed #

1. Make sure that you have installed the m2eclipse plugin for Eclispe. You can downlad it [here](http://www.eclipse.org/m2e/download/).

2. Check out the following Maven projects:
  * wsml4j-rdf-tool: http://kenai.com/projects/envision/sources/portal/show/tools
  * wsm4j-rdf-extension: http://kenai.com/projects/envision/sources/portal/show/api/wsmo4j-rdf-extensions
3. Import both projects as Maven projects into your workspace.

4. Install the latter project into your local maven repository (m2 folder) by running 'Maven->Install' with the m2eclipse plugin.

5. Open the WSML2RDFBatch test class which is included in the wsml4j-rdf-tool project and add the local path of the ontologies in the 'files' arraylist.

6. Define the serialization format (either RDF/XML or N3) and run the test.

7. That`s it. The translated files are stored in the local folders where the translated WSML serializations are stored.