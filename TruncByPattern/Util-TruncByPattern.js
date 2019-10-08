// cerner_2^5_2019
// JavaScript utility function to extract from large blob of text/string the first chunk of text that matches given pattern
// JavaScript utility function to truncate blob of text/string at the earliest postion where matching pattern is found
// (or at max position if none match)
// Based on https://medium.com/poka-techblog/simplify-your-javascript-use-map-reduce-and-filter-bd02c593cc2d 
//
// Example1: First message from email conversation
//   truncByPattern(emailConversation, [/(^From: )/m, /(-+Original Message-+)/m, /On\s+.+\w@\w.+\s+wrote:/m])
//
function truncByPattern (blobstr, patts = [/\W+/], maxPos=2000) {       
    //some guardrails
    if (blobstr.length == 0) { return blobstr } //empty blob string
    if (maxPos <= 0) { maxPos=2000 }            // default max position
    if (patts.length == 0 ) { patts = [/\W+/] } // default pattern: truncate at first non-word

    //MAP function: transform list of patterns into list of position indexes  
    var fnFindPos = (r => Object.prototype.toString.call(r) == '[object RegExp]' ? blobstr.search(r) : blobstr.indexOf(r) );
    //var fnFindPos = (r => r instanceof RegExp ? blobstr.search(r) : blobstr.indexOf(r) );
    
    //REDUCE function: from list of position indexes, calculate minimum non-zero value that is within maxPos  
    var fnFirstPos = ((firstPos, pattPos) => Math.min((pattPos>0 && pattPos<firstPos) ? pattPos : firstPos, maxPos));
    
    //return substring upto calculated position index
    return blobstr.slice(0, patts.map(fnFindPos).reduce(fnFirstPos, maxPos));
}
