// HireHub — Main JavaScript

function toggleMenu() {
  const links = document.querySelector('.nav-links');
  const auth = document.querySelector('.nav-auth > div');
  links?.classList.toggle('open');
  auth?.classList.toggle('open');
}

// Auto-dismiss flash messages
document.addEventListener('DOMContentLoaded', () => {
  const alerts = document.querySelectorAll('.alert');
  alerts.forEach(alert => {
    setTimeout(() => {
      alert.style.opacity = '0';
      alert.style.transition = 'opacity .5s';
      setTimeout(() => alert.remove(), 500);
    }, 4000);
  });

  // Job type badge color mapper
  document.querySelectorAll('.job-type-badge').forEach(badge => {
    const text = badge.textContent.trim().toLowerCase();
    if (text.includes('remote')) badge.style.background = 'rgba(16,185,129,0.15)';
    if (text.includes('intern')) badge.style.background = 'rgba(139,92,246,0.15)';
  });
});

// Confirm dialogs
function confirmDelete(msg) {
  return confirm(msg || 'Are you sure you want to delete this?');
}
