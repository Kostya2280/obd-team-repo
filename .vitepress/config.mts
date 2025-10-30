import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  srcDir: "./src",
  base: "/obd-team-repo/",
  
  title: "KPI Forms",
  description: "A VitePress Site",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Project', link: '/project' }
    ],

    sidebar: [
      {
        text: 'Pages',
        items: [
          { text: 'Вступ', link: '/intro' },
		  { text: 'Аналіз предметної області', link: '/docs/requirements/state-of-the-art' },
		  { text: 'Призначення розроблюваної системи', link: '/appointment' },
		  { text: 'Аналіз зацікавлених осіб для системи організації та управління опитуваннями експертів', link: '/docs/requirements/stakeholders-needs' },
		  { text: 'Розробка загальної діаграми прецедентів', link: '/precedent'},
		  { text: 'Реляційна схема', link: '/relational-schema'},  
		  { text: 'UseCase', link: '/use-case'},
		  { text: 'Бізнес модель та діаграма системи опитування', link: '/business-models'},
		  { text: 'Реалізація інформаційного та програмного забезпечення', link: '/SQL' },
		  { text: 'Реалізація об’єктно-реляційного відображення', link: '/implementation' },
		  { text: 'Висновки', link: '/vusnovki' },
		  { text: 'Автори', link: '/authors' },
        ]
      }
    ]
  }
})
