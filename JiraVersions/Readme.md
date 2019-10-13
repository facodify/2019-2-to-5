# Jira Unresolved Versions
Groovy example of filter, map, reduce (or find, collect, inject) to find Jira versions that are overdue with unresolved issues.

This assumes that following JIRA rest api calls are used to collect this data:
- jiraVersions = list of versions with details like "overdue" : GET /rest/api/2/project/{projectIdOrKey}/versions 
- for each version above, get count of unresolved issues : GET /rest/api/2/version/{id}/unresolvedIssueCount 