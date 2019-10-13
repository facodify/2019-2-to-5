// cerner_2^5_2019
// Groovy example of filter, map, reduce to find Jira versions that are overdue with unresolved issues
// Assume that following JIRA rest api calls are used to collect this data:
//   jiraVersions = list of versions with details like "overdue" : GET /rest/api/2/project/{projectIdOrKey}/versions 
//   for each version above, get count of unresolved issues : GET /rest/api/2/version/{id}/unresolvedIssueCount 
//

// Example data for demo
/*
def jiraVersions = 
[
    [
        "self": "http://www.example.com/jira/rest/api/2/version/10000",
        "id": "10000",
        "description": "An excellent version",
        "name": "New Version 1",
        "archived": false,
        "released": true,
        "releaseDate": "2010-07-06",
        "overdue": true,
        "userReleaseDate": "6/Jul/2010",
        "projectId": 10000,
        "issuesUnresolvedCount": 23
    ],
    [
        "self": "http://www.example.com/jira/rest/api/2/version/10000",
        "id": "10001",
        "description": "Another excellent version",
        "name": "New Version 2",
        "archived": false,
        "released": true,
        "releaseDate": "2012-10-01",
        "overdue": true,
        "userReleaseDate": "1/Oct/2012",
        "projectId": 10000,
        "issuesUnresolvedCount": 27
    ],
    [
        "self": "http://www.example.com/jira/rest/api/2/version/10010",
        "id": "10010",
        "description": "Minor Bugfix version",
        "name": "Next Version",
        "archived": false,
        "released": false,
        "overdue": false,
        "projectId": 10000
    ]
];
*/

// closure to look for overdue versions
def isDue = { it.overdue == true };

// OPTIONAL closure to transform / collect name, release date and unresolved issue count
def getDetails = { [ "name" : it.name, "releaseDate" : it.releaseDate, "issuesUnresolvedCount": it.issuesUnresolvedCount ] }

// closure to accumulate sum of all unresolved issue count for those versions
def getTotal = { total, it -> total + it.issuesUnresolvedCount }

// Now use above closures to find total number of unresolved issues
def dueTotal = jiraVersions.findAll(isDue).collect(getDetails).inject(0, getTotal);

// OPTIONAL output the result to screen
println "Total unresolved issues: ${dueTotal}";
jiraVersions.findAll(isDue).collect(getDetails).forEach() { it ->
  println "${it.name}  ${it.releaseDate}  ${it.issuesUnresolvedCount}"
}