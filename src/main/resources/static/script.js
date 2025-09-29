document.addEventListener('DOMContentLoaded', () => {
  const API_BASE_URL = 'http://localhost:8080/api';

  const expensesUrl = `${API_BASE_URL}/expenses`;
  const incomesUrl = `${API_BASE_URL}/incomes`;

  let expenses = [];
  let incomes = [];


  async function fetchExpenses() {
  try {
    const response = await fetch(`${expensesUrl}/all`);
    if (!response.ok) throw new Error('Failed to fetch expenses');
    expenses = await response.json();
    renderExpenseList();
    renderDashboard();  // Keep this here to update dashboard whenever expenses updated
  } catch (error) {
    console.error('Error loading expenses:', error);
  }
}


  async function fetchIncomes() {
    try {
      const response = await fetch(`${incomesUrl}/all`);
      if (!response.ok) throw new Error('Failed to fetch incomes');
      incomes = await response.json();
      renderIncomeList();
      renderDashboard();
    } catch (error) {
      console.error('Error loading incomes:', error);
    }
  }

  function renderDashboard() {
    const totalIncome = incomes.reduce((acc, inc) => acc + inc.amount, 0);
    const totalExpenses = expenses.reduce((acc, exp) => acc + Math.abs(exp.amount), 0);
    const balance = totalIncome - totalExpenses;

    document.getElementById("total-income").textContent = `$${totalIncome.toFixed(2)}`;
    document.getElementById("total-expenses").textContent = `$${totalExpenses.toFixed(2)}`;
    document.getElementById("balance").textContent = `$${balance.toFixed(2)}`;

    const transactions = [...incomes.map(i => ({ ...i, category: 'Income' })), ...expenses]
      .sort((a, b) => new Date(b.date) - new Date(a.date))
      .slice(0, 5);

    const tbody = document.getElementById("recent-transactions");
    tbody.innerHTML = "";
    transactions.forEach(tx => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${tx.id}</td>
        <td>${tx.date}</td>
        <td>${tx.title}</td>
        <td>${tx.category}</td>
        <td>${tx.description}</td>
        <td style="color: ${tx.amount < 0 ? 'red' : 'green'};">
          $${Math.abs(tx.amount).toFixed(2)}
        </td>
      `;
      tbody.appendChild(row);
    });
  }

  function renderExpenseList() {
    const tbody = document.getElementById("expense-list-body");
    tbody.innerHTML = "";

    expenses.forEach(exp => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${exp.id}</td>
        <td>${exp.date}</td>
        <td>${exp.title}</td>
        <td>${exp.category}</td>
        <td>${exp.description || ''}</td>
        <td style="color: red;">${Math.abs(exp.amount).toFixed(2)}</td>
        <td><button data-id="${exp.id}" class="delete-expense">Delete</button></td>
      `;
      tbody.appendChild(row);
    });


    document.querySelectorAll(".delete-expense").forEach(button => {
      button.onclick = async function () {
        const expenseId = this.getAttribute("data-id");
        const expenseToDelete = expenses.find(exp => exp.id == expenseId);
        try {
          const response = await fetch(`${expensesUrl}/${expenseId}`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(expenseToDelete), // Send full expense object as per backend expectation
          });
          if (!response.ok) throw new Error('Failed to delete expense');
          await fetchExpenses();
        } catch (error) {
          alert('Error deleting expense: ' + error.message);
        }
      };
    });
  }


    function renderIncomeList() {
        const tbody = document.getElementById("income-list-body");
        tbody.innerHTML = "";
        incomes.forEach(inc => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${inc.id}</td>
                <td>${inc.date}</td>
                <td>${inc.title}</td>
                <td>${inc.category}</td>
                <td>${inc.description || ''}</td>
                <td style="color: green;">${inc.amount.toFixed(2)}</td>
                <td><button data-id="${inc.id}" class="delete-income">Delete</button></td>
               
            `;
            tbody.appendChild(row);
        });
    
    


    document.querySelectorAll(".delete-income").forEach(button => {
        button.onclick = async function () {
            const incomeId = this.getAttribute("data-id");
            const incomeToDelete = incomes.find(inc => inc.id == incomeId);
            try {
                const response = await fetch(`${incomesUrl}/${incomeId}`, {
                    method: 'DELETE',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(incomeToDelete), // Send full income object as per backend expectation
                });
                if (!response.ok) throw new Error('Failed to delete income');
                await fetchIncomes();
            } catch (error) {
                alert('Error deleting income: ' + error.message);
            }
        };
    });
}



  

  function renderReports() {
    const categoryTotals = {};
    expenses.forEach(exp => {
      if (!categoryTotals[exp.category]) categoryTotals[exp.category] = 0;
      categoryTotals[exp.category] += Math.abs(exp.amount);
    });

    const ctxPie = document.getElementById('expenses-category-chart').getContext('2d');
    new Chart(ctxPie, {
      type: 'pie',
      data: {
        labels: Object.keys(categoryTotals),
        datasets: [{
          data: Object.values(categoryTotals),
          backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
        }]
      }
    });

    const dateMap = {};
    expenses.forEach(exp => {
      if (!dateMap[exp.date]) dateMap[exp.date] = 0;
      dateMap[exp.date] += Math.abs(exp.amount);
    });

    const dates = Object.keys(dateMap).sort();
    const amounts = dates.map(date => dateMap[date]);

    const ctxLine = document.getElementById('expenses-time-chart').getContext('2d');
    new Chart(ctxLine, {
      type: 'line',
      data: {
        labels: dates,
        datasets: [{
          label: 'Expenses Over Time',
          data: amounts,
          fill: false,
          borderColor: 'red',
          tension: 0.1
        }]
      }
    });
  }

  document.getElementById("expense-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const title = document.getElementById("expense-title").value.trim();
    const amount = parseFloat(document.getElementById("expense-amount").value);
    const category = document.getElementById("expense-category").value;
    const date = document.getElementById("expense-date").value;

    if (!title || amount <= 0 || !category || !date) {
      alert("Please fill out all fields correctly.");
      return;
    }

    const newExpense = { title, amount: -amount, category, date };

    try {
      const response = await fetch(`${expensesUrl}/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newExpense),
      });
      if (!response.ok) throw new Error('Failed to add expense');
      alert("Expense added!");
      this.reset();
      await fetchExpenses();
    } catch (error) {
      alert('Error adding expense: ' + error.message);
    }
  });

  document.getElementById("income-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const title = document.getElementById("income-title").value.trim();
    const amount = parseFloat(document.getElementById("income-amount").value);
    const date = document.getElementById("income-date").value;

    if (!title || amount <= 0 || !date) {
      alert("Please fill out all fields correctly.");
      return;
    }

    const newIncome = { title, amount, date };

    try {
      const response = await fetch(`${incomesUrl}/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newIncome),
      });
      if (!response.ok) throw new Error('Failed to add income');
      alert("Income added!");
      this.reset();
      await fetchIncomes();
    } catch (error) {
      alert('Error adding income: ' + error.message);
    }
  });

  document.querySelectorAll('nav a').forEach(link => {
    link.addEventListener('click', e => {
      e.preventDefault();
      const sectionToShow = e.target.getAttribute('data-section');
      document.querySelectorAll('main > section').forEach(sec => {
        sec.style.display = (sec.id === sectionToShow) ? 'block' : 'none';
      });
      if (sectionToShow === 'dashboard') renderDashboard();
      else if (sectionToShow === 'expense-list') renderExpenseList();
      else if (sectionToShow === 'income-list') renderIncomeList();
      else if (sectionToShow === 'reports') renderReports();
    });
  });

  Promise.all([fetchExpenses(), fetchIncomes()]).then(() => {
    document.querySelector('nav a[data-section="dashboard"]').click();
  });
});
