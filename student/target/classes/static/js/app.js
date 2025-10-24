const { createApp } = Vue;

createApp({
  data() {
    return {
      students: [],
      newStudent: {
        name: "",
        email: "",
        phone: "",
        address: "",
        birthDate: ""
      },
      apiUrl: "http://localhost:8080/api/students",
      searchQuery: "",
      showEditModal: false,
      showCreateModal: false,
      showDeleteModal: false,
      showToast: false,
      toastMessage: "",
      editStudentData: {},
      deleteStudentData: {},
      originalStudentData: {}
    };
  },
  methods: {

    // fetch all students
    async fetchStudents() {
      const res = await fetch(this.apiUrl);
      this.students = await res.json();
    },

    // add student function
    async addStudent() {
      if (!this.newStudent.name) {
        this.showToastAlert("Name is required");
        return;
      }
      const res = await fetch(this.apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.newStudent)
      });
      if (res.ok) {
        this.newStudent = { name: "", email: "", phone: "", address: "", birthDate: "" };
        this.showToastAlert("Student added successfully");
        this.clearSearch();
        this.fetchStudents();
        this.closePopUp();
      } else {
        const err = await res.json();
        this.showToastAlert(err.error || "Failed to add student");
      }
    },

    // search students function
    async searchStudents() {
      const query = this.searchQuery.trim();
      if (!query) {
        this.fetchStudents();
        return;
      }

      const res = await fetch(`${this.apiUrl}/search?name=${encodeURIComponent(query)}`);
      if (res.ok) {
        this.students = await res.json();
      } else {
        this.students = [];
      }
    },

    // clear search bar
    clearSearch() {
      this.searchQuery = "";
      this.fetchStudents();
    },

    // delete student function
    async confirmDelete() {
      const id = this.deleteStudentData.id;
      try {
        // console.log("Current id: " + id)
        await fetch(`${this.apiUrl}/${id}`, { method: "DELETE" });
        this.closePopUp();
        this.fetchStudents();
        this.showToastAlert(`Successfully deleted student ID ${id}`);
      } catch (error) {
        this.showToastAlert("Failed to delete student");
      }
    },


    // modal cases
    deleteStudent(student) {
      this.deleteStudentData = { id: student.studentId, name: student.name };
      this.showDeleteModal = true;
    },

    createStudent() {
      this.showCreateModal = true;
    },

    editStudent(student) {
      this.editStudentData = { ...student };
      this.originalStudentData = { ...student };
      this.showEditModal = true;
    },

    // close all modals
    closePopUp() {
      this.showEditModal = false;
      this.showCreateModal = false;
      this.showDeleteModal = false;
      this.editStudentData = {};
      this.originalStudentData = {};
    },

    // confirm edit function
    async confirmEdit() {
      const id = this.editStudentData.studentId;
      const res = await fetch(`${this.apiUrl}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.editStudentData)
      });

      // Compare old vs new to find changed fields
      if (res.ok) {
        const changedFields = [];
        for (let key in this.editStudentData) {
          if (this.editStudentData[key] !== this.originalStudentData[key]) {
            changedFields.push(key);
          }
        }

        const message =
          changedFields.length > 0
            ? `Updated fields: ${changedFields.join(", ")}`
            : "No changes detected.";

        this.showToastAlert(message);
        this.closePopUp();
        this.fetchStudents();
      } else {
        const err = await res.json().catch(() => ({}));
        this.showToastAlert(err.error || "Failed to update student");
      }
    },
    
    showToastAlert(message) {
      this.toastMessage = message;
      this.showToast = true;

      setTimeout(() => {
        this.showToast = false;
      }, 3000);
    }
  },
  mounted() {
    this.fetchStudents();
  }
}).mount("#app");
