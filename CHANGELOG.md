# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
