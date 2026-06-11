import { test, expect } from '@playwright/test';

test('homepage loads', async ({ page }) => {
  // the Angular build outputs an app under `dist/front-bibliotheque/browser`
  await page.goto('http://localhost:4173/browser');
  await expect(page).toHaveTitle(/Bibliothèque|Bibliotheque|Angular/);
});
