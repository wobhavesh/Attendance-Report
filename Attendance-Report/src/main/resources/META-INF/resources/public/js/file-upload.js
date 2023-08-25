/* Bootstrap 5 JS included */

console.clear();
('use strict');


// Drag and drop - single or multiple image files
// https://www.smashingmagazine.com/2018/01/drag-drop-file-uploader-vanilla-js/
// https://codepen.io/joezimjs/pen/yPWQbd?editors=1000
(function() {

	'use strict';

	// Four objects of interest: drop zones, input elements, gallery elements, and the files.
	// dataRefs = {files: [image files], input: element ref, gallery: element ref}

	const preventDefaults = event => {
		event.preventDefault();
		event.stopPropagation();
	};

	const highlight = event =>
		event.target.classList.add('highlight');

	const unhighlight = event =>
		event.target.classList.remove('highlight');

	const getInputAndGalleryRefs = element => {
		const zone = element.closest('.upload_dropZone') || false;
		const gallery = zone.querySelector('.upload_gallery') || false;
		const input = zone.querySelector('input[type="file"]') || false;
		return { input: input, gallery: gallery };
	}

	const handleDrop = event => {
		const dataRefs = getInputAndGalleryRefs(event.target);
		dataRefs.files = event.dataTransfer.files;
		handleFiles(dataRefs);
	}


	const eventHandlers = zone => {

		const dataRefs = getInputAndGalleryRefs(zone);
		if (!dataRefs.input) return;

		// Prevent default drag behaviors
		;['dragenter', 'dragover', 'dragleave', 'drop'].forEach(event => {
			zone.addEventListener(event, preventDefaults, false);
			document.body.addEventListener(event, preventDefaults, false);
		});

		// Highlighting drop area when item is dragged over it
		;['dragenter', 'dragover'].forEach(event => {
			zone.addEventListener(event, highlight, false);
		});
		;['dragleave', 'drop'].forEach(event => {
			zone.addEventListener(event, unhighlight, false);
		});

		// Handle dropped files
		zone.addEventListener('drop', handleDrop, false);

		// Handle browse selected files
		dataRefs.input.addEventListener('change', event => {
			dataRefs.files = event.target.files;
			handleFiles(dataRefs);
		}, false);

	}


	// Initialise ALL dropzones
	const dropZones = document.querySelectorAll('.upload_dropZone');
	for (const zone of dropZones) {
		eventHandlers(zone);
	}


	// No 'image/gif' or PDF or webp allowed here, but it's up to your use case.
	// Double checks the input "accept" attribute
	const isImageFile = file =>
		['image/jpeg', 'image/png', 'image/svg+xml'].includes(file.type);


	function previewFiles(dataRefs) {
		if (!dataRefs.gallery) return;
		for (const file of dataRefs.files) {
			let reader = new FileReader();
			reader.readAsDataURL(file);
			reader.onloadend = function() {
				let img = document.createElement('img');
				img.className = 'upload_img mt-2';
				img.setAttribute('alt', file.name);
				img.src = reader.result;
				dataRefs.gallery.appendChild(img);
			}
		}
	}

	// Based on: https://flaviocopes.com/how-to-upload-files-fetch/
	const imageUpload = dataRefs => {

		// Multiple source routes, so double check validity
		if (!dataRefs.files || !dataRefs.input) return;

		const url = dataRefs.input.getAttribute('data-post-url');
		if (!url) return;

		const name = dataRefs.input.getAttribute('data-post-name');
		if (!name) return;

		const formData = new FormData();
		formData.append(name, dataRefs.files[0]);

		const thisForm = document.getElementById('file-upload-form');
		var formData1 = new FormData(thisForm);

		console.log('form data---', formData);
		fetch(url, {
			method: 'POST',
			body: formData1
		})
			.then(res => {
				if (res.status == 200) {
					return res.json();
				} else {
					showError(true);
				}
			})
			.then(data => {
				console.log('posted: ', data);
				showError(false);
				if (data.length > 0) {
					var tableBody = document.getElementById('error-table');

					const tableData = data.map((value, index) => {
						return (
							`<tr>
								<th scope="row">${index + 1}</th>
								<td scope="col">${value.cellAddress}</td>
								<td scope="col">${value.message}</td>
								<td scope="col">${value.actualValue}</td>
						    </tr>`
						);
					}).join('');
					tableBody.innerHTML = tableData;

				} else {
					console.log('URL: ', url, '  name: ', name)
				}
			})
			.catch(error => {
				showError(true);
				console.error('errored: ', error);
			});
	}

	const showError = error => {
		if (error) {
			var successImport = document.getElementById('success-import-panel');
			if (successImport.style.display != "none") {
				successImport.style.display = "none";
			}
			var failedImport = document.getElementById('failed-import-panel');
			if (failedImport.style.display === "none") {
				failedImport.style.display = "block";
			}
			var cellErrorTable = document.getElementById('cell-error-panel');
			if (cellErrorTable.style.display != "none") {
				cellErrorTable.style.display = "none";
			}
		} else {
			var successImport = document.getElementById('success-import-panel');
			if (successImport.style.display === "none") {
				successImport.style.display = "block";
			}
			var failedImport = document.getElementById('failed-import-panel');
			if (failedImport.style.display != "none") {
				failedImport.style.display = "none";
			}
			var cellErrorTable = document.getElementById('cell-error-panel');
			if (cellErrorTable.style.display === "none") {
				cellErrorTable.style.display = "block";
			}
		}
	}


	// Handle both selected and dropped files
	const handleFiles = dataRefs => {

		let files = [...dataRefs.files];

		// Remove unaccepted file types
		/*files = files.filter(item => {
		  if (!isImageFile(item)) {
			console.log('Not an image, ', item.type);
		  }
		  return isImageFile(item) ? item : null;
		});*/

		if (!files.length) return;
		dataRefs.files = files;

		//previewFiles(dataRefs);
		imageUpload(dataRefs);
	}

})();