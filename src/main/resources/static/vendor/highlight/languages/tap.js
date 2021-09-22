 

function(hljs) {
  return {
    case_insensitive: true,
    contains: [
      hljs.HASH_COMMENT_MODE,
             {
        className: 'meta',
        variants: [
          { begin: '^TAP version (\\d+)$' },
          { begin: '^1\\.\\.(\\d+)$' }
        ],
      },
             {
        begin: '(\s+)?---$', end: '\\.\\.\\.$',
        subLanguage: 'yaml',
        relevance: 0
      },
	         {
        className: 'number',
        begin: ' (\\d+) '
      },
	         {
        className: 'symbol',
        variants: [
          { begin: '^ok' },
          { begin: '^not ok' }
        ],
      },
    ]
  };
}
