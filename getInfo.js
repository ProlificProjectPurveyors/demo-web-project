
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


/*$.get( "clubInfo.txt", function( data ) {
  alert( "Data Loaded: " + data );
});*/ //data == textFile.result

var i = 1;
var j = 1;
var loopCounter = 0;
$.get( "clubInfo.txt", function( data ) {
        //alert(textFile.result[1]+ textFile.result[2] + textFile.result[3]);
		//roomNumber.appendChild(textFile.result[i]);
		while(i+2< data.length)
		{
		var myLoc = data[i];
		//alert(myLoc);
		while(data[i+1] !== "\"")
		{
			myLoc = myLoc+data[i+1];
			//console.log(myLoc);
			i++;
		}
		
		var myLoc2= document.createTextNode(myLoc);
		roomNumber.appendChild(myLoc2);
		i = i+4;
		//roomNumber= newRow.insertCell(j);
		var theClubName = data[i];
		while(data[i+1] !== "\"")
		{
			theClubName = theClubName+data[i+1];
			//console.log(theClubName);
			i++;
		}
		
		var theClubName2= document.createTextNode(theClubName);
		clubName.appendChild(theClubName2);
		
		i = i+4;
		//console.log("Stuff here is " + i);
		//clubName = newRow.insertCell(j+1);
		var theDate = data[i];
		while(data[i+1] !== "\"")
		{
			theDate = theDate+data[i+1];
			//console.log(theDate);
			i++;
		}
		
		var theDate2= document.createTextNode(theDate);
		date.appendChild(theDate2);
		i = i+6;


		newRow   = tableRef.insertRow(tableRef.rows.length);
		roomNumber = newRow.insertCell(0);
		clubName  = newRow.insertCell(1);
		date = newRow.insertCell(2);
		loopCounter++;
		//date = newRow.insertCell(j+2);
		
		//console.log(textFile.result[i]+textFile.result[i+1]);
		//console.log(i);
		//console.log("End of file is " + textFile.result[45]);
		
		}
});
