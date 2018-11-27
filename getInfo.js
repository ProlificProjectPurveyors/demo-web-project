var t = document.getElementById("mainTable");
var trs = t.getElementsByTagName("tr");
var createRow = document.createAttribute("tr");

/*for(var i = 0; i < 6; i++)
{
	
	
}*/

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
/*
var newText  = document.createTextNode('New row');
var newText2  = document.createTextNode('New ro22w');
var newText3  = document.createTextNode('New ro5d55w');
var newText4  = document.createTextNode('New ro422w');
var newText5  = document.createTextNode('New roas22w');
var newText6  = document.createTextNode('Neaw ro422w');
/*
roomNumber.appendChild(newText);
clubName.appendChild(newText2);
date.appendChild(newText3);
food.appendChild(newText4);
major.appendChild(newText5);
descrip.appendChild(newText6);*/
var i = 1;
var j = 1;
var filesInput = document.getElementById("file");
filesInput.addEventListener("change", function (event) {
    var files = event.target.files;
    var file = files[0];
    var reader = new FileReader();
    reader.addEventListener("load", function (event) {
        var textFile = event.target;
        //alert(textFile.result[1]+ textFile.result[2] + textFile.result[3]);
		//roomNumber.appendChild(textFile.result[i]);
		//while(reader.AtEndOfStream)
		//{
		var myLoc = textFile.result[i];
		while(textFile.result[i+1] !== "\"")
		{
			myLoc = myLoc+textFile.result[i+1];
			//console.log(myLoc);
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
			//console.log(theClubName);
			i++;
		}
		
		var theClubName2= document.createTextNode(theClubName);
		clubName.appendChild(theClubName2);
		
		i = i+4;
		console.log("Stuff here is " + i);
		//clubName = newRow.insertCell(j+1);
		var theDate = textFile.result[i];
		while(textFile.result[i+1] !== "\"")
		{
			theDate = theDate+textFile.result[i+1];
			//console.log(theDate);
			i++;
		}
		
		var theDate2= document.createTextNode(theDate);
		date.appendChild(theDate2);
		//date = newRow.insertCell(j+2);
		if(i == textFile.result.length){
			//break;
		}
		//console.log(textFile.result[i]+textFile.result[i+1]);
		//console.log(i);
		//console.log("End of file is " + textFile.result[45]);
		
		//}
		
    });
    reader.readAsText(file);
});