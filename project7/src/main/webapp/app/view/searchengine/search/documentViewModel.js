Ext.define('Project7.view.searchengine.search.documentViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.documentViewModel',

    requires: [
        'Project7.view.searchengine.search.documentModel'
    ],

    formulas: {
        typeFilter: function (get) {
            var category = get('category');
            return this.filters[category];
        }
    },

    filters: {
        all:   [ 'news', 'forum' ],
        news:  [ 'news' ],
        forum: [ 'forum' ]
    },

    stores: {
        news: {
            type: 'news',
            autoLoad: true,
            sorters: [
                { property: 'date', direction: 'DESC' }
            ],
            filters: {
                property: 'type',
                operator: 'in',
                value: '{typeFilter}'
            }
        }
    }
});
