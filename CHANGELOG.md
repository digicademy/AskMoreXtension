# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.5.0] - 2024-05-16

### Added
- added new flag !ENCODE_URL to AskMoreAnnotations 

## [1.4.2] - 2023-11-27

### Fixed
- fixed method org.adwmainz.da.extensions.askmore.factories.SelectableOptionFactory.java#createOption() to remove enclosing quotes once again after introducing this bug in 1.4.1

## [1.4.1] - 2023-11-22

### Fixed
- fixed method org.adwmainz.da.extensions.askmore.factories.SelectableOptionFactory.java#createOption() to be able to handle pipe symbols
- fixed method org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser.java#createDialogModel() to be able to handle pipe symbols

### Fixed
- fixed method org.adwmainz.da.extensions.askmore.factories.SelectableOptionFactory.java#createOption() to be able to handle pipe symbols
- fixed method org.adwmainz.da.extensions.askmore.utils.AskMoreAnnotationParser.java#createDialogModel() to be able to handle pipe symbols

## [1.4.0] - 2022-11-04

### Added
- added class org.adwmainz.da.extensions.askmore.operations.FullySelectElementsOperation.java

## [1.3.0] - 2020-07-30

### Added
- added class org.adwmainz.da.extensions.askmore.operations.AnnotatedXQueryUpdateOperation.java
- added new helper class org.adwmainz.da.extensions.askmore.factories.PositionedInfoFactory.java
- added new helper class org.adwmainz.da.extensions.askmore.utils.RegexUtils.java
- added new helper class org.adwmainz.da.extensions.askmore.utils.XPathAnnotationParser.java
- added new helper methods to org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils.java
- added new helper methods to org.adwmainz.da.extensions.askmore.utils.ArgumentParser.java
- added new helper methods to org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider.java
- added localized default value INPUT_DIALOG_CLOSED
- added AskMoreAnnotation parsing to param elementLocation of class org.adwmainz.da.extensions.askmore.operations.DisplayInResultsViewOperation
- added XPathAnnotation parsing to param message of class org.adwmainz.da.extensions.askmore.operations.DisplayInResultsViewOperation
- added AskMoreAnnotation parsing to param externalParams of class org.adwmainz.da.extensions.askmore.operations.AnnotatedXSLTOperation
- added AskMoreAnnotation parsing to param externalParams of class org.adwmainz.da.extensions.askmore.operations.AnnotatedXQueryOperation

### Fixed
- fixed org.adwmainz.da.extensions.askmore.operations.\*.java#doOperation() to throw an exception if the generated input dialog is closed
- fixed method org.adwmainz.da.extensions.askmore.operations.DisplayInResultsViewOperation#doOperation() to be able to handle non-node XPath results
- fixed method org.adwmainz.da.extensions.askmore.utils.APIAccessUtils.java#containsNode()


## [1.2.0] - 2020-05-14

### Added
- added class org.adwmainz.da.extensions.askmore.operations.DisplayMessageOperation
- added param removeSelection to class org.adwmainz.da.extensions.askmore.operations.InsertOrReplaceAnnotatedFragmentOperation to offer a way of switching off this default behavior of its super class

### Fixed
- fixed method org.adwmainz.da.extensions.askmore.utils.APIAccessUtils#containsNode() to enable the class org.adwmainz.da.extensions.askmore.operations.InsertAnnotatedFragmentToSelectionOperation to handle incomplete selections
- fixed description of the params dialogTitle and message


## [1.1.0] - 2020-04-08

### Added
- added class org.adwmainz.da.extensions.askmore.operations.AnnotatedCommandLineOperation
- added helper class org.adwmainz.da.extensions.askmore.models.EditableArgumentDescriptor
- added helper class org.adwmainz.da.extensions.askmore.utils.AskMoreArgumentProvider
- added localized default value XPATH_RESULTS

### Deprecated
- added @deprecated to static fields of helper class org.adwmainz.da.extensions.askmore.utils.ArgumentDescriptorUtils

### Fixed
- fixed the description of AskMoreAnnotations displayed in oXygen for all extended AuthorOperations
