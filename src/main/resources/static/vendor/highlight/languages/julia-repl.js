 

function(hljs) {
  return {
    contains: [
      {
        className: 'meta',
        begin: /^julia>/,
        relevance: 10,
        starts: {
                                end: /^(?![ ]{6})/,
          subLanguage: 'julia'
      },
                                         aliases: ['jldoctest']
      }
    ]
  }
}
