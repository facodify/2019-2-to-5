// cerner_2^5_2019
// JavaScript utility function to format date
// Based on https://stackoverflow.com/a/23593278
// + Modified to handle 24-hour vs 12-hour time with am/pm
// + Modified to handle string name for month and day
// + Modified to handle year 'y' in same/single replace function
// Examples:
//   dt = new Date();
//   console.log("JIRA format: " + date2str(dt, 'd/MMM/yy h:m a'));
//   console.log(" ISO format: " + date2str(dt, 'YYYY-MM-DD HH:mm:ss'));
//   console.log("Any1 format: " + date2str(dt, 'dddd MMM dd yyyy HH:mm:Ss'));
//   console.log("Any2 format: " + date2str(dt, "dddd dd MMM 'yy hh:mm A"));
//
function date2str(xDate, xFormat) {
    //use this map to find parts of date/time string value
	var dMap = {
		M: xDate.getMonth() + 1,
		MMM: xDate.toString().split(" ")[1].slice(0,3),
		d: xDate.getDate(),
		ddd: xDate.toString().split(" ")[0].slice(0,3),
		H: xDate.getHours(), //24-hr
		h: xDate.getHours()>12 ? xDate.getHours()-12 : ( xDate.getHours()==0 ? 12 : xDate.getHours() ), //12-Hr
		a: xDate.getHours()>=12 ? "PM" : "AM",
		m: xDate.getMinutes(),
		s: xDate.getSeconds(),
		y: xDate.getFullYear()
	};
	//replace any uppercase format letters to lowercase (excluding case sensitive ones like H/h and M/m)
	xFormat = xFormat.replace(/Y/g, 'y');  //change to lowercase 'y' for year
	xFormat = xFormat.replace(/D/g, 'd');  //change to lowercase 'd' for day
	xFormat = xFormat.replace(/S/g, 's');  //change to lowercase 's' for second
	xFormat = xFormat.replace(/A/g, 'a');  //change to lowercase 'a' for am/pm

	let xDateOut = xFormat.replace(/(M+|d+|h+|H+|m+|s+|a+|y+)/g, function(v) {
		//Check for string output for Month or Day with length >= 3
		if (v.length>=3 && (v.slice(-1)=="M" || v.slice(-1)=="d")) {
			return dMap[v.slice(0,3)];
		} else { //all numeric output
			return ((v.length > 1 ? "0" : "") + dMap[v.slice(-1)]).slice( v.slice(-1)=="y" ? -v.length : -2 );
		}
	});
	return xDateOut;
}