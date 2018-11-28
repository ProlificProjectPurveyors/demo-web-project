
var tableRef = document.getElementById('mainTable').getElementsByTagName('tbody')[0];

// Insert a row in the table at the last row
var newRow   = tableRef.insertRow(tableRef.rows.length);

// Insert a cell in the row at index 0

var roomNumber = newRow.insertCell(0);
var clubName  = newRow.insertCell(1);
var date = newRow.insertCell(2);
var food = newRow.insertCell(3);
var major = newRow.insertCell(4);
var descrip = newRow.insertCell(5);
// Append a text node to the cell

var i = 1;
var j = 1;
var loopCounter = 0;
var filesInput = document.getElementById("file");

console.log(document.getElementById("file"));
filesInput.addEventListener("change", function (event) {
    var files = event.target.files;
	
    var file = files[0];
    var reader = new FileReader();
    reader.addEventListener("load", function (event) {
        var textFile = event.target;

		while(i+2< textFile.result.length)
		{
		var myLoc = textFile.result[i];

		while(textFile.result[i+1] !== "\"")
		{

			myLoc = myLoc+textFile.result[i+1];
			i++;
		}
		
		var myLoc2= document.createTextNode(myLoc);
		roomNumber.appendChild(myLoc2);
		i = i+4;
		//roomNumber= newRow.insertCell(j);
		var theClubName = textFile.result[i];
		while(textFile.result[i+1] !== "\"")
		{
			theClubName = theClubName+textFile.result[i+1];

			i++;
		}
		
		var theClubName2= document.createTextNode(theClubName);
		clubName.appendChild(theClubName2);
		
		i = i+4;

		//clubName = newRow.insertCell(j+1);
		var theDate = textFile.result[i];
		while(textFile.result[i+1] !== "\"")
		{
			theDate = theDate+textFile.result[i+1];

			i++;
		}
		
		var theDate2= document.createTextNode(theDate);
		date.appendChild(theDate2);
		//date = newRow.insertCell(j+2);
		i = i+6;


		newRow   = tableRef.insertRow(tableRef.rows.length);
		roomNumber = newRow.insertCell(0);
		clubName  = newRow.insertCell(1);
		date = newRow.insertCell(2);
		loopCounter++;
		}

		
    });
    reader.readAsText(file);
});
