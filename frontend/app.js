const API_BASE = 'http://localhost:8080/api';
let allApplicants = [];
let selectedApplicants = new Set();
let sortCol = 'applicantId';
let sortAsc = true;
let availableLanguages = [];
let selectedLanguagesList = [];
let globalSkills = [];
let globalInterests = [];

// Utility: Fetch Languages Catalog (from open source API)
async function fetchLanguages() {
    try {
        const response = await fetch('https://raw.githubusercontent.com/umpirsky/language-list/master/data/en/language.json');
        if (response.ok) {
            const data = await response.json();
            // The API returns an object { "en": "English", ... }. We want just the names.
            availableLanguages = Object.values(data).filter(lang => lang !== 'Filipino');
            setupLanguageSearch();
        }
    } catch (e) {
        console.error("Failed to fetch languages catalog", e);
    }
}

function setupLanguageSearch() {
    const searchInput = document.getElementById('languageSearch');
    const dropdown = document.getElementById('languageDropdown');
    const tagsContainer = document.getElementById('selectedLanguages');

    const renderDropdown = (query = '') => {
        dropdown.innerHTML = '';
        const lowerQuery = query.toLowerCase();
        
        const filtered = availableLanguages
            .filter(lang => !selectedLanguagesList.includes(lang) && lang.toLowerCase().includes(lowerQuery))
            .slice(0, 50);

        if (filtered.length === 0) {
            dropdown.innerHTML = '<div style="color: #999;">No matches found</div>';
            return;
        }

        filtered.forEach(lang => {
            const div = document.createElement('div');
            div.textContent = lang;
            div.addEventListener('mousedown', (e) => {
                e.preventDefault(); 
                addLanguageTag(lang);
                searchInput.value = '';
                dropdown.style.display = 'none';
            });
            dropdown.appendChild(div);
        });
    };

    const addLanguageTag = (lang) => {
        if (!selectedLanguagesList.includes(lang)) {
            selectedLanguagesList.push(lang);
            renderTags();
        }
    };

    const removeLanguageTag = (lang) => {
        selectedLanguagesList = selectedLanguagesList.filter(l => l !== lang);
        renderTags();
    };

    const renderTags = () => {
        tagsContainer.innerHTML = '';
        selectedLanguagesList.forEach(lang => {
            const tag = document.createElement('div');
            tag.className = 'tag';
            tag.innerHTML = `${lang} <span onclick="window.removeLang('${lang}')">&times;</span>`;
            tagsContainer.appendChild(tag);
        });
    };

    window.removeLang = removeLanguageTag;

    searchInput.addEventListener('focus', () => {
        dropdown.style.display = 'block';
        renderDropdown(searchInput.value);
    });

    searchInput.addEventListener('input', (e) => {
        dropdown.style.display = 'block';
        renderDropdown(e.target.value);
    });

    searchInput.addEventListener('blur', () => {
        dropdown.style.display = 'none';
    });
}

// Utility: Fetch Catalogs and Populate Form Checkboxes
async function fetchCatalogs() {
    try {
        // Fetch Skills
        const skillsResponse = await fetch(`${API_BASE}/catalog/skills`);
        if (skillsResponse.ok) {
            globalSkills = await skillsResponse.json();
            const skillContainer = document.getElementById('skillCheckboxes');
            skillContainer.innerHTML = ''; 
            globalSkills.forEach(skill => {
                const label = document.createElement('label');
                label.innerHTML = `<input type="checkbox" name="skillCodes" value="${skill.skillCode}"> ${skill.acquiredSkills}`;
                skillContainer.appendChild(label);
            });
        }

        // Fetch Interests
        const interestsResponse = await fetch(`${API_BASE}/catalog/interests`);
        if (interestsResponse.ok) {
            globalInterests = await interestsResponse.json();
            const interestContainer = document.getElementById('interestCheckboxes');
            interestContainer.innerHTML = ''; 
            globalInterests.forEach(interest => {
                const label = document.createElement('label');
                // Note: Updated to correctly use areasOfInterest based on the backend model
                label.innerHTML = `<input type="checkbox" name="interestCodes" value="${interest.interestCode}"> ${interest.areasOfInterest}`;
                interestContainer.appendChild(label);
            });
        }
    } catch (error) {
        console.error("Error fetching catalogs:", error);
    }
}

// Utility: Get selected checkboxes by name
function getSelectedCheckboxes(name) {
    const checkboxes = document.querySelectorAll(`input[name="${name}"]:checked`);
    return Array.from(checkboxes).map(cb => cb.value);
}

// Handle Form Submission
async function handleFormSubmit(e) {
    e.preventDefault();
    const formMessage = document.getElementById('formMessage');
    formMessage.textContent = 'Submitting...';
    formMessage.className = 'form-message';

    try {
        const skillCodes = getSelectedCheckboxes('skillCodes');
        const interestCodes = getSelectedCheckboxes('interestCodes');
        if (skillCodes.length === 0) {
            throw new Error("Please select at least one skill.");
        }
        if (interestCodes.length === 0) {
            throw new Error("Please select at least one area of interest.");
        }

        const payload = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            middleName: document.getElementById('middleName').value,
            nickname: document.getElementById('nickname').value,
            gender: document.getElementById('gender').value,
            birthdate: document.getElementById('birthdate').value.replace(/\//g, '-'),
            birthplace: document.getElementById('birthplace').value,
            citizenship: document.getElementById('citizenship').value,

            emailAddress: document.getElementById('emailAddress').value,
            mobilePhone: document.getElementById('mobilePhone').value,
            phoneLandline: document.getElementById('phoneLandline').value,
            address: document.getElementById('address').value,
            permanentAddress: document.getElementById('permanentAddress').value,

            height: parseFloat(document.getElementById('height').value),
            weight: parseFloat(document.getElementById('weight').value),

            employerName: document.getElementById('employerName').value,
            highestEducLevel: document.getElementById('highestEducLevel').value,
            educSchoolAndAddress: document.getElementById('educSchoolAndAddress').value,
            educDegree: document.getElementById('educDegree').value,
            educFrom: parseInt(document.getElementById('educFrom').value, 10),
            educTo: parseInt(document.getElementById('educTo').value, 10),

            availabilityOption: document.getElementById('availabilityOption').value === 'Days of the Week' 
                ? 'Days of the Week - ' + document.getElementById('daysOfWeekInput').value
                : document.getElementById('availabilityOption').value,
            hoursPerDay: parseInt(document.getElementById('hoursPerDay').value, 10),
            willingToDeploy: document.getElementById('willingToDeploy').checked,
            willingHumanitarian: document.getElementById('willingHumanitarian').checked,

            languages: selectedLanguagesList,
            skillCodes: skillCodes,
            interestCodes: interestCodes,

            contactPerson: document.getElementById('contactPerson').value,
            contactRelationship: document.getElementById('contactRelationship').value,
            contactPhoneNo: document.getElementById('contactPhoneNo').value,
            contactAddress: document.getElementById('contactAddress').value,

            referenceNameRelationship: document.getElementById('referenceNameRelationship').value,
            companyOrgInfo: document.getElementById('companyOrgInfo').value,
            position: document.getElementById('position').value,

            licenseCertification: document.getElementById('licenseCertification').value ? {
                licenseCertification: document.getElementById('licenseCertification').value,
                dateIssued: document.getElementById('dateIssued').value ? document.getElementById('dateIssued').value.replace(/\//g, '-') : null
            } : null,
            volunteerEngagement: document.getElementById('nameOfCompany').value ? {
                nameOfCompany: document.getElementById('nameOfCompany').value,
                positionHeld: document.getElementById('positionHeldVE').value,
                lengthOfServiceFrom: document.getElementById('lengthOfServiceFrom').value ? parseInt(document.getElementById('lengthOfServiceFrom').value, 10) : null,
                lengthOfServiceTo: document.getElementById('lengthOfServiceTo').value ? parseInt(document.getElementById('lengthOfServiceTo').value, 10) : null,
                volunteerEngagementSummary: document.getElementById('volunteerEngagementSummary').value
            } : null
        };

        const response = await fetch(`${API_BASE}/applicants`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errData = await response.json().catch(() => null);
            throw new Error(errData?.error || `HTTP error ${response.status}`);
        }

        const result = await response.json();
        formMessage.textContent = 'Registration successful! Applicant ID: ' + result.applicantId;
        formMessage.className = 'form-message text-success';
        document.getElementById('volunteerForm').reset();
        
        // Reset custom language tags
        selectedLanguagesList = [];
        document.getElementById('selectedLanguages').innerHTML = '';
        
        if (document.getElementById('adminDashboard').style.display === 'block') {
            fetchApplicants();
        }

    } catch (error) {
        console.error("Submit Error:", error);
        formMessage.textContent = 'Error: ' + error.message;
        formMessage.className = 'form-message text-danger';
    }
}

// Admin Login Logic
function setupAdminLogin() {
    const modal = document.getElementById('adminLoginModal');
    const btnAuth = document.getElementById('btnAdminAuth');
    const btnCancel = document.getElementById('btnCancelLogin');
    const btnSubmit = document.getElementById('btnSubmitLogin');
    const dashboard = document.getElementById('adminDashboard');
    const errorMsg = document.getElementById('loginError');

    btnAuth.addEventListener('click', () => {
        if (btnAuth.textContent === 'Logout') {
            dashboard.style.display = 'none';
            btnAuth.textContent = 'Admin Login';
            selectedApplicants.clear();
            localStorage.removeItem('isAdminLoggedIn');
        } else {
            modal.style.display = 'flex';
            errorMsg.style.display = 'none';
            document.getElementById('adminUsername').value = '';
            document.getElementById('adminPassword').value = '';
        }
    });

    btnCancel.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    btnSubmit.addEventListener('click', () => {
        const user = document.getElementById('adminUsername').value;
        const pass = document.getElementById('adminPassword').value;
        if (user === 'admin' && pass === '123') {
            modal.style.display = 'none';
            dashboard.style.display = 'block';
            btnAuth.textContent = 'Logout';
            localStorage.setItem('isAdminLoggedIn', 'true');
            fetchApplicants();
        } else {
            errorMsg.style.display = 'block';
        }
    });

    const handleEnter = (e) => {
        if (e.key === 'Enter') btnSubmit.click();
    };
    document.getElementById('adminUsername').addEventListener('keydown', handleEnter);
    document.getElementById('adminPassword').addEventListener('keydown', handleEnter);

    // Restore login state on page load
    if (localStorage.getItem('isAdminLoggedIn') === 'true') {
        dashboard.style.display = 'block';
        btnAuth.textContent = 'Logout';
        fetchApplicants();
    }
}

// Fetch and Render Applicants (Admin)
async function fetchApplicants() {
    const tableBody = document.getElementById('applicantsTableBody');
    tableBody.innerHTML = '<tr><td colspan="7" class="text-center">Loading applicants...</td></tr>';

    try {
        const response = await fetch(`${API_BASE}/applicants`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'X-Role': 'ADMIN'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error ${response.status}`);
        }

        allApplicants = await response.json();
        renderApplicants();

    } catch (error) {
        console.error("Fetch Applicants Error:", error);
        tableBody.innerHTML = `<tr><td colspan="7" class="text-center text-danger">Failed to load applicants: ${error.message}</td></tr>`;
    }
}

// Render Applicants with sorting and searching
function renderApplicants() {
    const tableBody = document.getElementById('applicantsTableBody');
    const searchQuery = document.getElementById('adminSearch').value.toLowerCase();
    
    // Filter
    let filtered = allApplicants.filter(app => {
        const fullName = `${app.firstName} ${app.lastName}`.toLowerCase();
        const email = app.emailAddress.toLowerCase();
        const id = app.applicantId ? app.applicantId.toString().toLowerCase() : '';
        return fullName.includes(searchQuery) || email.includes(searchQuery) || id.includes(searchQuery);
    });

    // Sort
    filtered.sort((a, b) => {
        let valA = a[sortCol] || '';
        let valB = b[sortCol] || '';
        if (typeof valA === 'string') valA = valA.toLowerCase();
        if (typeof valB === 'string') valB = valB.toLowerCase();
        if (valA < valB) return sortAsc ? -1 : 1;
        if (valA > valB) return sortAsc ? 1 : -1;
        return 0;
    });

    tableBody.innerHTML = '';
    if (filtered.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="7" class="text-center">No applicants found.</td></tr>';
        return;
    }

    filtered.forEach(app => {
        const row = document.createElement('tr');
        const skillsLen = app.skills ? app.skills.length : 0;
        const intLen = app.interests ? app.interests.length : 0;
        
        row.innerHTML = `
            <td style="text-align: center;"><input type="checkbox" class="row-select" value="${app.applicantId}" ${selectedApplicants.has(app.applicantId.toString()) ? 'checked' : ''}></td>
            <td>${app.applicantId}</td>
            <td>${app.lastName}, ${app.firstName}</td>
            <td>${app.emailAddress}</td>
            <td>${app.mobilePhone}</td>
            <td>${skillsLen} Skills / ${intLen} Interests</td>
            <td>
                <button class="btn btn-outline btn-small" onclick="editApplicant('${app.applicantId}')">View/Edit</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    // Add event listeners to newly rendered checkboxes
    document.querySelectorAll('.row-select').forEach(cb => {
        cb.addEventListener('change', (e) => {
            if (e.target.checked) {
                selectedApplicants.add(e.target.value);
            } else {
                selectedApplicants.delete(e.target.value);
            }
        });
    });

    // Sync select-all checkbox state
    const selectAllCb = document.getElementById('selectAllApplicants');
    if (selectAllCb) {
        const rowCbs = Array.from(document.querySelectorAll('.row-select'));
        selectAllCb.checked = rowCbs.length > 0 && rowCbs.every(cb => cb.checked);
    }
}

// Setup Admin Dashboard UI
function setupAdminDashboard() {
    document.getElementById('adminSearch').addEventListener('input', renderApplicants);
    
    document.querySelectorAll('.sortable').forEach(th => {
        th.addEventListener('click', () => {
            const col = th.getAttribute('data-sort');
            if (sortCol === col) {
                sortAsc = !sortAsc;
            } else {
                sortCol = col;
                sortAsc = true;
            }
            renderApplicants();
        });
    });

    document.getElementById('btnRefreshApplicants').addEventListener('click', fetchApplicants);
    
    // Select All Logic
    const selectAllCb = document.getElementById('selectAllApplicants');
    if (selectAllCb) {
        selectAllCb.addEventListener('change', (e) => {
            const isChecked = e.target.checked;
            const rowCbs = document.querySelectorAll('.row-select');
            rowCbs.forEach(cb => {
                cb.checked = isChecked;
                if (isChecked) {
                    selectedApplicants.add(cb.value);
                } else {
                    selectedApplicants.delete(cb.value);
                }
            });
        });
    }

    // Delete Selected Logic
    const btnDeleteSelected = document.getElementById('btnDeleteSelected');
    if (btnDeleteSelected) {
        btnDeleteSelected.addEventListener('click', async () => {
            const selected = Array.from(selectedApplicants);
            if (selected.length === 0) {
                alert("Please select at least one record to delete.");
                return;
            }
            if (!confirm(`Are you sure you want to delete ${selected.length} record(s)? This action cannot be undone.`)) {
                return;
            }

            try {
                // We need to send the X-Role: ADMIN header to authorize the delete
                await Promise.all(selected.map(async (id) => {
                    const res = await fetch(`${API_BASE}/applicants/${id}`, { 
                        method: 'DELETE',
                        headers: { 'X-Role': 'ADMIN' }
                    });
                    if (!res.ok) throw new Error(`Failed to delete ${id}`);
                }));
                alert(`Successfully deleted ${selected.length} record(s).`);
                selectedApplicants.clear();
                fetchApplicants();
                if (selectAllCb) selectAllCb.checked = false;
            } catch (err) {
                alert('Error deleting records: ' + err.message);
            }
        });
    }

    document.getElementById('btnCloseEditModal').addEventListener('click', () => {
        document.getElementById('editApplicantModal').style.display = 'none';
    });
}

// Edit Applicant (Modal)
let currentEditingId = null;

window.editApplicant = function(id) {
    const app = allApplicants.find(a => a.applicantId === id);
    if (!app) return;
    
    currentEditingId = id;
    const modal = document.getElementById('editApplicantModal');
    const content = document.getElementById('editFormContent');
    
    // We create a dynamically generated form snippet just for editing core details
    // In a real app we'd map all 25+ fields, here we show a representative subset
    content.innerHTML = `
        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Personal Information</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>First Name</label>
            <input type="text" id="editFirstName" value="${app.firstName || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Middle Name</label>
            <input type="text" id="editMiddleName" value="${app.middleName || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Last Name</label>
            <input type="text" id="editLastName" value="${app.lastName || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Nickname</label>
            <input type="text" id="editNickname" value="${app.nickname || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Gender</label>
            <div class="custom-select-container" id="customEditSelectGender">
                <div class="custom-select-trigger ${app.gender ? 'has-value' : ''}" id="triggerEditGender">
                    <span>${app.gender || 'Select...'}</span>
                    <span style="font-size: 0.8rem;">▼</span>
                </div>
                <div class="custom-select-options" id="optionsEditGender">
                    <div class="custom-select-option" data-value="Male">Male</div>
                    <div class="custom-select-option" data-value="Female">Female</div>
                    <div class="custom-select-option" data-value="Other">Other</div>
                </div>
                <input type="hidden" id="editGender" value="${app.gender || ''}">
            </div>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Birthdate</label>
            <input type="text" id="editBirthdate" value="${app.birthdate ? app.birthdate.replace(/-/g, '/') : ''}" placeholder="YYYY/MM/DD" pattern="\\d{4}/\\d{2}/\\d{2}" maxlength="10" oninput="this.value = this.value.replace(/[^0-9/]/g, '').replace(/^(\\d{4})(\\d)/, '$1/$2').replace(/^(\\d{4})\\/(\\d{2})(\\d)/, '$1/$2/$3');">
        </div>
        <div class="form-group margin-bottom-20 span-2">
            <label>Birthplace</label>
            <input type="text" id="editBirthplace" value="${app.birthplace || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Citizenship</label>
            <input type="text" id="editCitizenship" value="${app.citizenship || ''}">
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Contact Details</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Email Address</label>
            <input type="email" id="editEmail" value="${app.emailAddress || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Mobile Phone</label>
            <input type="tel" id="editMobile" value="${app.mobilePhone || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Phone/Landline</label>
            <input type="tel" id="editPhoneLandline" value="${app.phoneLandline || ''}">
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Current Address</label>
            <input type="text" id="editAddress" value="${app.address || ''}">
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Permanent Address</label>
            <input type="text" id="editPermanentAddress" value="${app.permanentAddress || ''}">
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Physical Details</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Height (cm)</label>
            <input type="number" step="0.01" id="editHeight" value="${app.height || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Weight (kg)</label>
            <input type="number" step="0.01" id="editWeight" value="${app.weight || ''}">
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Education & Employment</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Name of Employer</label>
            <input type="text" id="editEmployerName" value="${app.employerName || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Highest Educational Attainment</label>
            <div class="custom-select-container" id="customEditSelectEduc">
                <div class="custom-select-trigger ${app.highestEducLevel ? 'has-value' : ''}" id="triggerEditEduc">
                    <span>${app.highestEducLevel || 'Select...'}</span>
                    <span style="font-size: 0.8rem;">▼</span>
                </div>
                <div class="custom-select-options" id="optionsEditEduc">
                    <div class="custom-select-option" data-value="Post Graduate">Post Graduate</div>
                    <div class="custom-select-option" data-value="Tertiary">Tertiary</div>
                    <div class="custom-select-option" data-value="Secondary">Secondary</div>
                    <div class="custom-select-option" data-value="Elementary">Elementary</div>
                </div>
                <input type="hidden" id="editHighestEducLevel" value="${app.highestEducLevel || ''}">
            </div>
        </div>
        <div class="form-group margin-bottom-20">
            <label>School Name and Address</label>
            <input type="text" id="editEducSchool" value="${app.educSchoolAndAddress || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Degree / Course</label>
            <input type="text" id="editEducDegree" value="${app.educDegree || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>From Year</label>
            <input type="number" id="editEducFrom" value="${app.educFrom || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>To Year</label>
            <input type="number" id="editEducTo" value="${app.educTo || ''}">
        </div>
        <div class="form-group margin-bottom-20 span-2">
            <label>License or Certified Membership to any trade / Profession</label>
            <input type="text" id="editLicenseCertification" value="${app.licenseCertification && app.licenseCertification.licenseCertification ? app.licenseCertification.licenseCertification : ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Date of Issue</label>
            <input type="text" id="editDateIssued" value="${app.licenseCertification && app.licenseCertification.dateIssued ? app.licenseCertification.dateIssued.replace(/-/g, '/') : ''}" placeholder="YYYY/MM/DD" pattern="\\d{4}/\\d{2}/\\d{2}" maxlength="10" oninput="this.value = this.value.replace(/[^0-9/]/g, '').replace(/^(\\d{4})(\\d)/, '$1/$2').replace(/^(\\d{4})\\/(\\d{2})(\\d)/, '$1/$2/$3');">
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Previous Volunteer Engagement</h3>
        </div>
        <div class="form-group margin-bottom-20 span-2">
            <label>Name of Company/Organization</label>
            <input type="text" id="editNameOfCompany" value="${app.volunteerEngagement && app.volunteerEngagement.nameOfCompany ? app.volunteerEngagement.nameOfCompany : ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Position held</label>
            <input type="text" id="editPositionHeldVE" value="${app.volunteerEngagement && app.volunteerEngagement.positionHeld ? app.volunteerEngagement.positionHeld : ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Length of Service From (Year)</label>
            <input type="number" id="editLengthOfServiceFrom" value="${app.volunteerEngagement && app.volunteerEngagement.lengthOfServiceFrom ? app.volunteerEngagement.lengthOfServiceFrom : ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Length of Service To (Year)</label>
            <input type="number" id="editLengthOfServiceTo" value="${app.volunteerEngagement && app.volunteerEngagement.lengthOfServiceTo ? app.volunteerEngagement.lengthOfServiceTo : ''}">
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Volunteer Engagement Summary</label>
            <textarea id="editVolunteerEngagementSummary" rows="3" style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-family: inherit; font-size: 0.9rem; resize: none; overflow-y: auto;">${app.volunteerEngagement && app.volunteerEngagement.volunteerEngagementSummary ? app.volunteerEngagement.volunteerEngagementSummary : ''}</textarea>
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Volunteer Preferences</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Availability</label>
            <div class="custom-select-container" id="customEditSelectAvail">
                <div class="custom-select-trigger ${app.availabilityOption ? 'has-value' : ''}" id="triggerEditAvail">
                    <span>${app.availabilityOption || 'Select...'}</span>
                    <span style="font-size: 0.8rem;">▼</span>
                </div>
                <div class="custom-select-options" id="optionsEditAvail">
                    <div class="custom-select-option" data-value="As Required">As required</div>
                    <div class="custom-select-option" data-value="Weekends">Weekends</div>
                    <div class="custom-select-option" data-value="After Office Hours">After Office Hours</div>
                    <div class="custom-select-option" data-value="Holidays">Holidays</div>
                    <div class="custom-select-option" data-value="Days of the Week">Days of the Week</div>
                </div>
                <input type="hidden" id="editAvail" value="${app.availabilityOption || ''}">
            </div>
        </div>
        <div class="form-group margin-bottom-20 span-2">
            <label>Number of Hours per day or per engagement</label>
            <input type="number" id="editHoursPerDay" value="${app.hoursPerDay || ''}">
        </div>
        <div class="form-group checkbox-group span-3 margin-bottom-20" style="justify-content: flex-start; align-items: flex-start; gap: 15px;">
            <label>
                <input type="checkbox" id="editDeploy" ${app.willingToDeploy ? 'checked' : ''}>
                Willing to be deployed anywhere in the country?
            </label>
            <label>
                <input type="checkbox" id="editHumanitarian" ${app.willingHumanitarian ? 'checked' : ''}>
                Willing to work in Humanitarian Response?
            </label>
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Skills, Languages & Interests</h3>
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Language / Dialect Spoken</label>
            <div class="custom-multiselect">
                <input type="text" id="editLanguageSearch" placeholder="Type to search and select languages..." autocomplete="off">
                <div class="dropdown-list" id="editLanguageDropdown" style="display: none;"></div>
                <div class="selected-tags" id="editSelectedLanguages"></div>
            </div>
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Skills *</label>
            <details style="cursor: pointer; border: 1px solid #ccc; padding: 10px; border-radius: 4px;">
                <summary style="outline: none; font-weight: bold;">Click to edit skills...</summary>
                <div class="checkbox-grid" style="margin-top: 15px;">
                    ${globalSkills.map(skill => `
                        <label><input type="checkbox" name="editSkillCodes" value="${skill.skillCode}" ${app.skills && app.skills.find(s => s.skillCode === skill.skillCode) ? 'checked' : ''}> ${skill.acquiredSkills}</label>
                    `).join('')}
                </div>
            </details>
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Areas of Interest *</label>
            <details style="cursor: pointer; border: 1px solid #ccc; padding: 10px; border-radius: 4px;">
                <summary style="outline: none; font-weight: bold;">Click to edit interests...</summary>
                <div class="checkbox-grid" style="margin-top: 15px;">
                    ${globalInterests.map(interest => `
                        <label><input type="checkbox" name="editInterestCodes" value="${interest.interestCode}" ${app.interests && app.interests.find(i => i.interestCode === interest.interestCode) ? 'checked' : ''}> ${interest.areasOfInterest}</label>
                    `).join('')}
                </div>
            </details>
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Emergency Contact Person</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Contact Person Name</label>
            <input type="text" id="editContactPerson" value="${app.contactPerson || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Relationship</label>
            <input type="text" id="editContactRelationship" value="${app.contactRelationship || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Contact Mobile Phone</label>
            <input type="tel" id="editContactPhone" value="${app.contactPhoneNo || ''}">
        </div>
        <div class="form-group margin-bottom-20 span-3">
            <label>Contact Address</label>
            <input type="text" id="editContactAddress" value="${app.contactAddress || ''}">
        </div>

        <div class="form-group margin-bottom-20 span-3">
            <h3 style="margin: 5px 0; border-bottom: 1px solid #ddd; padding-bottom: 5px; color: var(--primary-red); font-size: 1.2rem;">Reference Person</h3>
        </div>
        <div class="form-group margin-bottom-20">
            <label>Reference Name & Relationship</label>
            <input type="text" id="editRefName" value="${app.referenceNameRelationship || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Company / Organization Name</label>
            <input type="text" id="editCompany" value="${app.companyOrgInfo || ''}">
        </div>
        <div class="form-group margin-bottom-20">
            <label>Business, Occupation (Position)</label>
            <input type="text" id="editPosition" value="${app.position || ''}">
        </div>
    `;
    
    modal.style.display = 'flex';

    // Set up languages tag input for the modal
    window.editSelectedLanguagesList = app.languages ? [...app.languages] : [];
    const editSearchInput = document.getElementById('editLanguageSearch');
    const editDropdown = document.getElementById('editLanguageDropdown');
    const editTagsContainer = document.getElementById('editSelectedLanguages');

    const renderEditTags = () => {
        editTagsContainer.innerHTML = '';
        window.editSelectedLanguagesList.forEach(lang => {
            const tag = document.createElement('div');
            tag.className = 'tag';
            tag.innerHTML = `${lang} <span onclick="window.removeEditLang('${lang}')">&times;</span>`;
            editTagsContainer.appendChild(tag);
        });
    };

    window.removeEditLang = (lang) => {
        window.editSelectedLanguagesList = window.editSelectedLanguagesList.filter(l => l !== lang);
        renderEditTags();
    };

    const renderEditDropdown = (query = '') => {
        editDropdown.innerHTML = '';
        const lowerQuery = query.toLowerCase();
        
        const filtered = availableLanguages
            .filter(lang => !window.editSelectedLanguagesList.includes(lang) && lang.toLowerCase().includes(lowerQuery))
            .slice(0, 50);

        if (filtered.length === 0) {
            editDropdown.innerHTML = '<div style="color: #999;">No matches found</div>';
            return;
        }

        filtered.forEach(lang => {
            const div = document.createElement('div');
            div.textContent = lang;
            div.addEventListener('mousedown', (e) => {
                e.preventDefault(); 
                if (!window.editSelectedLanguagesList.includes(lang)) {
                    window.editSelectedLanguagesList.push(lang);
                    renderEditTags();
                }
                editSearchInput.value = '';
                editDropdown.style.display = 'none';
            });
            editDropdown.appendChild(div);
        });
    };

    editSearchInput.addEventListener('focus', () => {
        editDropdown.style.display = 'block';
        renderEditDropdown(editSearchInput.value);
    });

    editSearchInput.addEventListener('input', (e) => {
        editDropdown.style.display = 'block';
        renderEditDropdown(e.target.value);
    });

    editSearchInput.addEventListener('blur', () => {
        editDropdown.style.display = 'none';
    });

    renderEditTags();
};

// Save Edit
document.getElementById('btnSaveEdit').addEventListener('click', async () => {
    if (!currentEditingId) return;
    
    // Find original app to preserve fields we didn't put in the modal
    const original = allApplicants.find(a => a.applicantId === currentEditingId);
    
    const editSkillCodes = getSelectedCheckboxes('editSkillCodes');
    const editInterestCodes = getSelectedCheckboxes('editInterestCodes');
    
    if (editSkillCodes.length === 0) {
        alert("Please select at least one skill.");
        return;
    }
    if (editInterestCodes.length === 0) {
        alert("Please select at least one area of interest.");
        return;
    }

    // We construct a DTO Request matching ApplicantRequest
    const payload = {
        applicantId: original.applicantId,
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        middleName: document.getElementById('editMiddleName').value,
        nickname: document.getElementById('editNickname').value,
        gender: document.getElementById('editGender').value,
        birthdate: document.getElementById('editBirthdate').value.replace(/\//g, '-'),
        birthplace: document.getElementById('editBirthplace').value,
        citizenship: document.getElementById('editCitizenship').value,
        emailAddress: document.getElementById('editEmail').value,
        mobilePhone: document.getElementById('editMobile').value,
        phoneLandline: document.getElementById('editPhoneLandline').value,
        address: document.getElementById('editAddress').value,
        permanentAddress: document.getElementById('editPermanentAddress').value,
        height: parseFloat(document.getElementById('editHeight').value),
        weight: parseFloat(document.getElementById('editWeight').value),
        employerName: document.getElementById('editEmployerName').value,
        highestEducLevel: document.getElementById('editHighestEducLevel').value,
        educSchoolAndAddress: document.getElementById('editEducSchool').value,
        educDegree: document.getElementById('editEducDegree').value,
        educFrom: parseInt(document.getElementById('editEducFrom').value, 10),
        educTo: document.getElementById('editEducTo').value ? parseInt(document.getElementById('editEducTo').value, 10) : null,
        availabilityOption: document.getElementById('editAvail').value,
        hoursPerDay: parseInt(document.getElementById('editHoursPerDay').value, 10),
        willingToDeploy: document.getElementById('editDeploy').checked,
        willingHumanitarian: document.getElementById('editHumanitarian').checked,
        languages: window.editSelectedLanguagesList,
        skillCodes: getSelectedCheckboxes('editSkillCodes'),
        interestCodes: getSelectedCheckboxes('editInterestCodes'),
        contactPerson: document.getElementById('editContactPerson').value,
        contactRelationship: document.getElementById('editContactRelationship').value,
        contactPhoneNo: document.getElementById('editContactPhone').value,
        contactAddress: document.getElementById('editContactAddress').value,
        referenceNameRelationship: document.getElementById('editRefName').value,
        companyOrgInfo: document.getElementById('editCompany').value,
        position: document.getElementById('editPosition').value,
        licenseCertification: document.getElementById('editLicenseCertification').value ? {
            licenseCertification: document.getElementById('editLicenseCertification').value,
            dateIssued: document.getElementById('editDateIssued').value ? document.getElementById('editDateIssued').value.replace(/\//g, '-') : null
        } : null,
        volunteerEngagement: document.getElementById('editNameOfCompany').value ? {
            nameOfCompany: document.getElementById('editNameOfCompany').value,
            positionHeld: document.getElementById('editPositionHeldVE').value,
            lengthOfServiceFrom: document.getElementById('editLengthOfServiceFrom').value ? parseInt(document.getElementById('editLengthOfServiceFrom').value, 10) : null,
            lengthOfServiceTo: document.getElementById('editLengthOfServiceTo').value ? parseInt(document.getElementById('editLengthOfServiceTo').value, 10) : null,
            volunteerEngagementSummary: document.getElementById('editVolunteerEngagementSummary').value
        } : null
    };

    try {
        const response = await fetch(`${API_BASE}/applicants/${currentEditingId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errData = await response.json().catch(() => null);
            throw new Error(errData?.error || `HTTP error ${response.status}`);
        }

        alert('Update successful!');
        document.getElementById('editApplicantModal').style.display = 'none';
        fetchApplicants();

    } catch (error) {
        alert('Update failed: ' + error.message);
    }
});


// Custom Dropdowns Logic
function setupCustomDropdowns() {
    // We can use event delegation for better performance and dynamic elements (like edit modal)
    document.addEventListener('click', (e) => {
        // 1. If clicking outside all dropdowns, close them
        if (!e.target.closest('.custom-select-container')) {
            document.querySelectorAll('.custom-select-options').forEach(opt => opt.classList.remove('open'));
            return;
        }

        // 2. If clicking a trigger
        const trigger = e.target.closest('.custom-select-trigger');
        if (trigger) {
            const container = trigger.closest('.custom-select-container');
            const options = container.querySelector('.custom-select-options');
            
            // Close others
            document.querySelectorAll('.custom-select-options').forEach(opt => {
                if (opt !== options) opt.classList.remove('open');
            });
            options.classList.toggle('open');
            return;
        }

        // 3. If clicking an option
        const option = e.target.closest('.custom-select-option');
        if (option) {
            const container = option.closest('.custom-select-container');
            const triggerText = container.querySelector('.custom-select-trigger span:first-child');
            const input = container.querySelector('input[type="hidden"]');
            const trigger = container.querySelector('.custom-select-trigger');
            
            triggerText.textContent = option.textContent;
            input.value = option.getAttribute('data-value');
            trigger.classList.add('has-value');
            option.parentElement.classList.remove('open');
            
            input.dispatchEvent(new Event('change', { bubbles: true }));
        }
    });
}

// Initialization
document.addEventListener('DOMContentLoaded', () => {
    fetchLanguages();
    fetchCatalogs();
    setupAdminLogin();
    setupAdminDashboard();
    setupCustomDropdowns();
    
    // Days of the week logic
    const availOption = document.getElementById('availabilityOption');
    const daysInput = document.getElementById('daysOfWeekInput');
    if (availOption && daysInput) {
        availOption.addEventListener('change', (e) => {
            if (e.target.value === 'Days of the Week') {
                daysInput.style.display = 'block';
                daysInput.required = true;
            } else {
                daysInput.style.display = 'none';
                daysInput.required = false;
                daysInput.value = '';
            }
        });
    }

    const form = document.getElementById('volunteerForm');
    if (form) {
        form.addEventListener('submit', handleFormSubmit);
    }
});
