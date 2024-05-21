# TODO

<label class="container">analyze while downloading
  <input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>
<label class="container">rework color scheme from Vendor
<input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>
<label class="container">analyze Terminal Logs and filter to build Terminal View
<input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>


<label class="container"> -- new -- 
<input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>
___

# Done
<label class="container"> Update Vendor view for height
<input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>
<label class="container">download from files
<input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>
<label class="container">rework selection of Logfiles with download
  <input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>
<label class="container">fix searching via DataStorage
<input type="checkbox" checked="checked">
  <span class="checkmark"></span>
</label>

<style>
/* The container */
.container {
  display: block;
  position: relative;
  padding-left: 35px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* Hide the browser's default checkbox */
.container input {
  position: absolute;
  opacity: 0;
  height: 0;
  width: 0;
  cursor: pointer;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute;
  top: 8px;
  left: 0;
  height: 20px;
  width: 20px;
  background-color: #3d3f46;
}

/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark {
  background-color: #ccc;
}

/* When the checkbox is checked, add a blue background */
.container input:checked ~ .checkmark {
  background-color: #2196F3;
}


/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.container input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.container .checkmark:after {
  left: 6px;
  top: 2px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}
</style>