import { defineConfig } from '@playwright/test';

export default defineConfig({
  testDir: 'e2e',
  testIgnore: ['**/src/**/*.spec.ts'],
  timeout: 30_000,
  use: {
    headless: true,
    viewport: { width: 1280, height: 720 },
    ignoreHTTPSErrors: true,
  },
  projects: [
    { name: 'chromium', use: { browserName: 'chromium' } },
  ],
});
