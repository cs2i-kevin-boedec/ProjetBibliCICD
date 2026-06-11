# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: example.spec.ts >> homepage loads
- Location: e2e\example.spec.ts:3:5

# Error details

```
Error: expect(page).toHaveTitle(expected) failed

Expected pattern: /Bibliothèque|Bibliotheque|Angular/
Received string:  "Index of /"
Timeout: 5000ms

Call log:
  - Expect "toHaveTitle" with timeout 5000ms
    14 × unexpected value "Index of /"

```

```yaml
- heading "Index of /" [level=1]
- table:
  - rowgroup:
    - row "(drw-rw-rw-) 11-juin-2026 12:18 browser/":
      - cell
      - cell "(drw-rw-rw-)":
        - code: (drw-rw-rw-)
      - cell "11-juin-2026 12:18"
      - cell:
        - code
      - cell "browser/":
        - link "browser/":
          - /url: ./browser/
    - row "(-rw-rw-rw-) 11-juin-2026 12:18 18.9k 3rdpartylicenses.txt":
      - cell
      - cell "(-rw-rw-rw-)":
        - code: (-rw-rw-rw-)
      - cell "11-juin-2026 12:18"
      - cell "18.9k":
        - code: 18.9k
      - cell "3rdpartylicenses.txt":
        - link "3rdpartylicenses.txt":
          - /url: ./3rdpartylicenses.txt
    - row "(-rw-rw-rw-) 11-juin-2026 12:18 18B prerendered-routes.json":
      - cell
      - cell "(-rw-rw-rw-)":
        - code: (-rw-rw-rw-)
      - cell "11-juin-2026 12:18"
      - cell "18B":
        - code: 18B
      - cell "prerendered-routes.json":
        - link "prerendered-routes.json":
          - /url: ./prerendered-routes.json
- text: Node.js v24.14.0/
- link "http-server":
  - /url: https://github.com/http-party/http-server
- text: server running @ localhost:4173
```

# Test source

```ts
  1 | import { test, expect } from '@playwright/test';
  2 | 
  3 | test('homepage loads', async ({ page }) => {
  4 |   await page.goto('http://localhost:4173');
> 5 |   await expect(page).toHaveTitle(/Bibliothèque|Bibliotheque|Angular/);
    |                      ^ Error: expect(page).toHaveTitle(expected) failed
  6 | });
  7 | 
```